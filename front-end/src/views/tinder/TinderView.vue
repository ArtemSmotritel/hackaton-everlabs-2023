<script setup>
import { onMounted, ref } from "vue";
import UsersApi from "../../api/users.api";

const users = ref([]);

onMounted(() => {
  users.value = UsersApi.getAll();
});
</script>

<template>
  <v-container class="bg-surface-variant mb-6" style="height: 100vh">
    <v-row align="center" justify="center" class="h-100">
      <v-col align-self="center"> </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
