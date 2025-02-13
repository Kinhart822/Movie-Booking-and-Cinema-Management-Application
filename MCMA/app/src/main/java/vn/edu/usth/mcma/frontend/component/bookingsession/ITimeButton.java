package vn.edu.usth.mcma.frontend.component.bookingsession;

import vn.edu.usth.mcma.frontend.network.apis.BookingApi;

public interface ITimeButton {
    void onClickListener(Long scheduleId, BookingApi.RegisterBookingSessionCallback callback);
}
