package mrs.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import mrs.domain.model.RoleName;
import mrs.domain.model.User;
import mrs.domain.service.reservation.ReservationService;
import mrs.domain.service.room.RoomService;

@Controller
@RequestMapping("reservations/{date}/{roomId}")
public class ReservationsController {
	@Autowired
	RoomService roomService;
	@Autowired
	ReservationService reservationService;
	
	@ModelAttribute
	ReservationForm setUpForm() {
		ReservationForm form = new ReservationForm();
		// デフォルト値
		form.setStartTime(LocalTime.of(9, 0));
		form.setEndTime(LocalTime.of(10, 0));
		return form;
	}
	
	@GetMapping
	String reserveForm(
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@PathVariable("date") LocalDate date,
		@PathVariable("roomId") Integer roomId, Model model) {
		ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
		List<Reservation> reservations = reservationService.findReservations(reservableRoomId);
		
		// 00:00から23:30まで30分単位でLocalDateオブジェクトを作成しリストに格納
		List<LocalTime> timeList = 
			Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30))
			.limit(24 * 2)
			.collect(Collectors.toList());
		
		model.addAttribute("room", roomService.findMeetingRoom(roomId));
		model.addAttribute("reservations", reservations);
		model.addAttribute("timeList", timeList);
		// 一旦ダミーユーザーを設定（後から認証ユーザーを設定）
		model.addAttribute("user", dummyUser());
		
		return "reservation/reserveForm";
	}
	
	private User dummyUser() {
		User user = new User();
		user.setUserId("taro-yamada");
		user.setFirstName("太郎");
		user.setLastName("山田");
		user.setRoleName(RoleName.USER);
		return user;
	}
}
