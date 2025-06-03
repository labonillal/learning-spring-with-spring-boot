package com.frankmoley.lil.landonhotel.web.controller;

import com.frankmoley.lil.landonhotel.service.RoomReservationService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/occupancy")
public class OccupancyController {

    private final RoomReservationService roomReservationService;

    public OccupancyController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }


    @GetMapping
    public String getReservations(@RequestParam(name = "date", required = false) String dateString, Model model){
        Date date = new Date();
        if (StringUtils.isNotBlank(dateString)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(dateString);
            } catch (Exception e) {
                //Do Nothing
            }
        }

        model.addAttribute("date", date);
        model.addAttribute("reservations", this.roomReservationService.getRoomReservationsForDate(dateString));

        return "occupancy";
    }
}
