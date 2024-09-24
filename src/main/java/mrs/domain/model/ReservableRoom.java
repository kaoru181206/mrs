package mrs.domain.model;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

/**
 * 特定の日に予約可能な会議室の情報を格納するテーブル
 */

@Entity
public class ReservableRoom implements Serializable {
	@EmbeddedId
	private ReservableRoomId reservableRoomId;
	
	@ManyToOne
	@JoinColumn(name = "room_id", insertable = false, updatable = false)
	@MapsId("roomId")
	private MeetingRoom meetingRoom;
	
	// コンストラクタ
	public ReservableRoom(ReservableRoomId reservableRoomId) {
		this.reservableRoomId = reservableRoomId;
	}
	
	public ReservableRoom() {
	}

	public ReservableRoomId getReservableRoomId() {
		return reservableRoomId;
	}

	public void setReservableRoomId(ReservableRoomId reservableRoomId) {
		this.reservableRoomId = reservableRoomId;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}
	
	
}
