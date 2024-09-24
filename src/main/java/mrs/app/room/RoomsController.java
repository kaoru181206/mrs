package mrs.app.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mrs.domain.model.ReservableRoom;
import mrs.domain.service.room.RoomService;

@Controller
@RequestMapping("rooms")
public class RoomsController {
	@Autowired
	RoomService roomService;
	
	// 本日日付の予約可能会議室一覧
	@GetMapping
	String listRooms(Model model) {
		LocalDate today = LocalDate.now();
		List<ReservableRoom> rooms = roomService.findReservableRooms(today);
		model.addAttribute("date", today);
		model.addAttribute("rooms", rooms);
		return "room/listRooms";
	}
	
	// 特定日付の予約可能会議室一覧
	// MEMO: @PathVariable URLに含まれる動的なパラメータ取得
	@GetMapping(value = "{date}")
	String listRooms(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@PathVariable("date")LocalDate date, Model model) {
		List<ReservableRoom> rooms = roomService.findReservableRooms(date);
		model.addAttribute("rooms", rooms);
		return "room/listRooms";
	}
}
