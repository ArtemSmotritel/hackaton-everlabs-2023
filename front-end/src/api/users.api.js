import HttpService from "../services/http.service.js";

class UsersApi {
  getAll() {
    return HttpService.get("users");
  }

  markMate(id) {
    const body = { matchId: id, userId: +sessionStorage.getItem("userId") };
    return HttpService.post("matches", body);
  }
}

export default new UsersApi();
