<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const drawer = ref(null);
const router = useRouter();

const pushRoute = (pathName) => {
  drawer.value = !drawer.value;
  router.push({ name: pathName });
};
</script>

<template>
  <v-layout class="d-flex flex-row justify-end mx-4 my-2" width="30%">
    <v-navigation-drawer v-model="drawer" location="right" temporary>
      <v-list density="compact" nav>
        <v-list-item prepend-icon="mdi-home-circle" title="Home" value="home" @click="pushRoute('home')"></v-list-item>
        <v-list-item
          prepend-icon="mdi-account-group"
          title="Make Mate"
          value="about"
          @click="pushRoute('match')"
        ></v-list-item>
        <v-list-item
          prepend-icon="mdi-view-dashboard"
          title="Dashboard"
          value="about"
          @click="pushRoute('event-board')"
        ></v-list-item>
      </v-list>
    </v-navigation-drawer>

    <div class="d-flex">
      <v-btn icon="mdi-menu" @click.stop="drawer = !drawer" variant="text" />
    </div>
  </v-layout>
</template>

<style scoped></style>
