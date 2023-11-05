<script setup>
import { onMounted, ref } from "vue";
import UsersApi from "../../api/users.api";
import TinderCard from "./TinderCard.vue";
import Swipeable from "./Swipeable.vue";
import AppNavbar from "../../components/AppNavbar.vue";

const users = ref([]);

const onSwipe = (direction) => {
  if (direction === "swipe-right") {
    UsersApi.markMate(users.value[0]["id"]);
  }

  setTimeout(() => users.value.shift(), 50);
};

onMounted(() => {
  users.value = UsersApi.getAll();
});
</script>

<template>
  <AppNavbar />

  <v-container class="mb-6 overflow-hidden" style="height: 100vh">
    <v-row align="center" justify="center" class="h-100">
      <v-col align-self="center" cols="12" lg="6" md="8" sm="10" xm="12">
        <div v-if="!users.length">
          <v-card class="mx-auto ma-4" max-width="80%">
            <v-img src="https://cdn-icons-png.flaticon.com/512/5787/5787110.png" height="80%" cover></v-img>
            <v-card-title class="text-center"
              >You do not have any new mates!
              <router-link :to="{ name: 'event-board' }">Explore events...</router-link></v-card-title
            >
          </v-card>
        </div>
        <template v-else> </template>
        <v-slide-y-transition v-for="(user, index) in users">
          <Swipeable :key="user.id" v-on:swipe="onSwipe">
            <TinderCard :key="user.id" :user="user" />
          </Swipeable>
        </v-slide-y-transition>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
