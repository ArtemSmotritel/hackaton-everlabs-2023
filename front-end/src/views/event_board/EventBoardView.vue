<script setup>
import { onMounted, ref } from "vue";
import EventBoardApi from "../../api/eventBoard.api";

import EventCard from "./EventCard.vue";

const generalEvents = ref([]);
const privateEvents = ref([]);

onMounted(() => {
  generalEvents.value = EventBoardApi.getAllGeneral();
  privateEvents.value = EventBoardApi.getAllPrivate();
});
</script>

<template>
  <v-container class="bg-surface-variant mb-6" style="height: 100vh">
    <v-row align="center" justify="center" class="h-100">
      <v-col align-self="center">
        <EventCard v-for="event in generalEvents" :key="event.id" :event="event" />
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
