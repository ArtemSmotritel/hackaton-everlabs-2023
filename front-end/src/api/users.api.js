import HttpService from "../services/http.service.js";

class UsersApi {
  getAll() {
    const randomUsers = Array(8).fill(null);
    return randomUsers.map(generateRandomUser);
  }
}

function generateRandomUser() {
  const randomId = Math.floor(Math.random() * 1000);
  const firstNames = ["John", "Jane", "Doe", "Michael", "Emily", "Chris", "Sarah", "David"];
  const lastNames = ["Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller"];
  const randomFirstName = firstNames[Math.floor(Math.random() * firstNames.length)];
  const randomLastName = lastNames[Math.floor(Math.random() * lastNames.length)];
  const randomAge = Math.floor(Math.random() * 80) + 18; // Assuming age is between 18 and 98
  const randomDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
  const interests = [
    { id: 1, name: "Hiking" },
    { id: 2, name: "Cooking" },
    { id: 3, name: "Reading" },
    { id: 4, name: "Gaming" },
    { id: 5, name: "Traveling" },
  ];
  const randomInterests = interests.sort(() => 0.5 - Math.random()).slice(0, 3);
  const randomAvatarUrl = `https://randomuser.me/api/portraits/men/${randomId}.jpg`;

  const randomUser = {
    id: randomId,
    firstName: randomFirstName,
    lastName: randomLastName,
    age: randomAge,
    description: randomDescription,
    interests: randomInterests,
    avatar_url: randomAvatarUrl,
  };

  return randomUser;
}

export default new UsersApi();
