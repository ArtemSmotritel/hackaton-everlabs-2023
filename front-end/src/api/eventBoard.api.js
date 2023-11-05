import HttpService from "../services/http.service.js";

class EventBoardApi {
  getAllGeneral() {
    const randomBoardEvents = Array(8).fill(null);
    return randomBoardEvents
      .map(generateRandomBoardEvents)
      .map((event) => ({ ...event, type: "PUBLIC" }));
  }

  getAllPrivate() {
    const randomBoardEvents = Array(8).fill(null);
    return randomBoardEvents
      .map(generateRandomBoardEvents)
      .map((event) => ({ ...event, type: "PRIVATE" }));
  }

  getRandom() {
    const randomBoardEvents = Array(8).fill(null);
    return randomBoardEvents.map(generateRandomBoardEvents);
  }
}

function generateRandomBoardEvents() {
  const randomId = Math.floor(Math.random() * 1000);
  const randomMatchCount = Math.floor(Math.random() * 20);
  const randomCreatedById = Math.floor(Math.random() * 1000);
  const isAccepted = Math.floor(Math.random() * 5) > 3;
  const names = [
    "Take a shit",
    "Dinner at John's",
    "Lunch at Gloria's",
    "Catch some monsters",
    "Reveal some conspiracies",
    "Group overthinking",
    "Answer some existential questions. Crying or dancing afterwards. Really depends on what answer you got.",
    "Have fun",
    "GG",
  ];
  const randomName = names[Math.floor(Math.random() * names.length)];

  const dates = [
    "04.11.2023 08:30",
    "05.11.2023 15:15",
    "06.11.2023 03:45",
    "07.11.2023 11:20",
    "08.11.2023 18:10",
    "09.11.2023 06:55",
    "10.11.2023 00:00",
  ];
  const randomDate = dates[Math.floor(Math.random() * dates.length)];

  const interests = [
    { id: 1, name: "Hiking" },
    { id: 2, name: "Cooking" },
    { id: 3, name: "Reading" },
    { id: 4, name: "Gaming" },
    { id: 5, name: "Traveling" },
    { id: 5, name: "Cycling" },
    { id: 5, name: "Boxing" },
    { id: 5, name: "Fighting" },
    { id: 5, name: "Rock" },
  ];
  const randomInterests = interests.sort(() => 0.5 - Math.random()).slice(0, Math.floor(Math.random() * interests.length));

  const randomBoardEvent = {
    id: randomId,
    name: randomName,
    date: randomDate,
    matchesCount: randomMatchCount,
    interests: randomInterests,
    createdBy: randomCreatedById,
    accepted: isAccepted,
    type: null,
  };

  return randomBoardEvent;
}

export default new EventBoardApi();
