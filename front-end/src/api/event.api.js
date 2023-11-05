import HttpService from "../services/http.service.js";

class EventApi {
  getOne(eventId) {
    return HttpService.get(`events/${eventId}`);
  }

  attendEvent(eventId) {
    return HttpService.post(`events/${eventId}/attend`, {});
  }

  skipEvent(eventId) {
    return HttpService.post(`events/${eventId}/skip`, {});
  }

  getAllGeneral() {
    return HttpService.get("events/public");
  }

  getAllPrivate() {
    return HttpService.get("events/private");
  }
}

export default new EventApi();
