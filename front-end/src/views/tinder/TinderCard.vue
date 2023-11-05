<script setup>
import AppInterestChip from "../../components/AppInterestChip.vue";
import { ref } from "vue";

defineProps({
  user: Object,
});

const show = ref(false);
const defaultUserThumbnailUrl = `https://randomuser.me/api/portraits/men/${Math.floor(Math.random() * 100)}.jpg`;

const getChipColor = (id) => {
  const allColors = ["success", "purple", "orange", "blue"];
  const randomOffset = id % allColors.length;

  return allColors[randomOffset];
};
</script>

<template>
  <v-card class="mx-auto ma-4" style="margin-bottom: 300px !important">
    <v-img :src="user.avatar_url || defaultUserThumbnailUrl" height="80%" cover :eager="true"></v-img>

    <v-card-title> {{ user.firstName }} {{ user.lastName }}, {{ user.age }}</v-card-title>
    <AppInterestChip v-for="interest in user.interests" :key="interest.id" :text="interest.name" :salt="interest.id" />

    <v-card-actions>
      <v-btn color="orange-lighten-2" variant="text" @click="show = !show">
        {{ show ? "Hide description" : "Show more" }}
      </v-btn>

      <v-spacer></v-spacer>
    </v-card-actions>

    <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>

        <v-card-text>{{ user.description }} </v-card-text>
      </div>
    </v-expand-transition>
  </v-card>
</template>

<style scoped></style>
