package com.frankmoley.lil.landonhotel.web.api;

import com.frankmoley.lil.landonhotel.data.entity.Reservation;
import com.frankmoley.lil.landonhotel.data.repository.ReservationRepository;
import com.frankmoley.lil.landonhotel.web.exception.BadRequestException;
import com.frankmoley.lil.landonhotel.web.exception.NotFoundException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationApiController {

    private final ReservationRepository reservationRepository;


    public ReservationApiController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<Reservation> getAllReservations(@PathVariable(value = "date", required = false) String dateString) {
        if (StringUtils.isNotEmpty(dateString)) {
            Date date = new Date(new java.util.Date().getTime());
            return this.reservationRepository.findAllByReservationDate(date);
        }

        return this.reservationRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation addReservation(@RequestBody Reservation reservation){
        return this.reservationRepository.save(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable("id") long id) {
        Optional<Reservation> reservation = this.reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new NotFoundException("reservation not found with id: " + id);
        }

        return reservation.get();
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable("id") long id, @RequestBody Reservation reservation) {
        if (id != reservation.getId()) {
            throw new BadRequestException("id on path doesn't match body");
        }

        return this.reservationRepository.save(reservation);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteGuest(@PathVariable("id") long id) {
        this.reservationRepository.deleteById(id);
    }
}
