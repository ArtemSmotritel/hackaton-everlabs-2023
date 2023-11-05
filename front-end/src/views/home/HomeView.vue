<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const inputValue = ref("");
const router = useRouter();

onMounted(() => {
  if (sessionStorage.getItem("userId") !== null) {
    inputValue.value = sessionStorage.getItem("userId");
  }
});

function openTinder() {
  router.push("/mate-match");
}

function openBoard() {
  router.push("/events");
}

function setSessionUserId() {
  sessionStorage.setItem("userId", inputValue.value);
}
</script>

<template>
  <v-container>
    <v-row class="h-screen mx-auto">
      <div class="button-size">
        <v-col class="h-50 buttons-padding" cols="12">
          <v-btn
            @click="openTinder"
            class="home-button tinder-bac-img h-100 w-100 text-h3"
            block
            size="x-large"
            ><span class="text-on-button">Make Mate</span></v-btn
          >
        </v-col>

        <v-col class="h-50 buttons-padding" cols="12">
          <v-btn
            @click="openBoard"
            class="home-button board-bac-img h-100 w-100 text-h3"
            size="x-large"
            ><span class="text-on-button">Events</span></v-btn
          >
        </v-col>
      </div>

      <v-row class="h-25">
        <v-col cols="9">
          <v-text-field v-model="inputValue" label="Enter Id"></v-text-field>
        </v-col>
        <v-col cols="3">
          <v-btn
            @click="setSessionUserId"
            class="save-button text-body-2"
            block
            size="x-large"
            >Save</v-btn
          >
        </v-col>
      </v-row>
    </v-row>
  </v-container>
</template>

<style scoped>
.home-button {
  background-size: cover;
  box-sizing: border-box;
}
.tinder-bac-img {
  background-image: url("@/assets/tinder.jpg");
}
.board-bac-img {
  background-image: url("@/assets/board.jpg");
}

.text-on-button {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
  width: calc(100% + 20px);
  height: 70px;
  z-index: 4;
  background-color: rgba(105, 40, 234, 0.592);
  color: rgb(255, 255, 255);
  font-size: larger;
  font-weight: 900;
}
.button-size {
  height: 90%;
  width: 100%;
}
.buttons-padding {
  padding: 12px 0;
}
.save-button {
  height: 56px;
  color: white;
  font-weight: 700;
  background-color: rgb(105, 40, 234);
}
</style>
