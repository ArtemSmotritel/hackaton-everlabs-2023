<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import AppInterestChip from "../../components/AppInterestChip.vue";
import AppUserChip from "../../components/AppUserChip.vue";
import EventApi from "../../api/event.api";

const router = useRouter();
const route = useRoute();

const eventData = ref({});

function goToEvents() {
  router.push("/events");
}

function attendEvent() {
  EventApi.attendEvent(+route.params.eventId);
  // Like an imbecile I am
  eventData.value = {
    ...eventData.value,
    accepted: true,
  };
}

function skipEvent() {
  EventApi.skipEvent(+route.params.eventId);
  // Like an imbecile I am
  eventData.value = {
    ...eventData.value,
    accepted: false,
  };
}

onMounted(async () => {
  eventData.value = (await EventApi.getOne(+route.params.eventId)).data;
});
</script>

<template>
  <v-container class="h-screen size-event-details-page">
    <v-row>
      <v-col class="title-of-event-details standart-style" cols="12">
        <button class="to-back-button" @click="goToEvents">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 16 16"
          >
            <path
              d="M6.646 8.146a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.647a.5.5 0 0 0-.708-.708l-4 4a.5.5 0 0 0 0 .708z"
            />
          </svg>
        </button>

        <span class="text-h3 text-titel-event-details">{{
          eventData.name
        }}</span>
      </v-col>

      <v-col class="place-and-date standart-style" cols="12">
        <span class="text-subtitle-1 text-transfer">{{ eventData.place }}</span>
        <span class="text-subtitle-2 text-transfer">{{ eventData.date }}</span>
      </v-col>

      <v-col class="standart-style description" cols="12">
        <span class="text-subtitle-1 text-transfer"
          ><span class="text-for-explain-chip">Description:</span
          >{{ eventData.description }}</span
        >
      </v-col>

      <v-col class="attendees standart-style" cols="12">
        <span class="text-for-explain-chip">People attendees:</span>
        <AppUserChip
          v-for="person in eventData.participants"
          :key="person.id"
          :text="`${person.firstName} ${person.lastName}`"
        />
      </v-col>

      <v-col class="standart-style" cols="12">
        <span class="text-for-explain-chip">Interesting of ivent: </span>
        <AppInterestChip
          v-for="interest in eventData.interests"
          :key="interest.id"
          :text="interest.name"
        />
      </v-col>

      <v-col class="join-button-box" cols="12">
        <v-btn
          v-if="!eventData.accepted"
          @click="attendEvent"
          color="success"
          size="x-large"
          >Attend</v-btn
        >
        <v-btn v-else @click="skipEvent" color="warning" size="x-large"
          >Skip</v-btn
        >
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.size-event-details-page {
  width: 90%;
  padding-top: 20px;
}

.title-of-event-details {
  display: flex;
  align-items: center;
  background-color: blueviolet;
}

.text-titel-event-details {
  color: white;
  font-weight: 700;
}

.text-transfer {
  word-wrap: break-word;
}

.place-and-date {
  color: white;
  background-color: rgb(137, 43, 226);
  padding: none;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.description {
  font-weight: 700;
  color: rgb(0, 0, 0);
  background-color: rgb(255, 255, 255);
}

.standart-style {
  margin-top: 10px;
  border-radius: 5px;
  box-shadow: 4px 4px 8px 2px rgba(0, 0, 0, 0.2);
}

.text-for-explain-chip {
  font-weight: 600;
}

.join-button-box {
  display: flex;
  justify-content: center;
}

.to-back-button {
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
  margin-right: 10px;
  width: 30px;
  height: 40px;
  transition: background-color 0.5s ease;
}

.to-back-button:active {
  background-color: rgb(99, 82, 82);
}
</style>
