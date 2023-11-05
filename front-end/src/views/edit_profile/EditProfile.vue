<template>
  <v-sheet class="main-edit-profile-block">
    <span >Profile</span>
    <v-form @submit.prevent="submitForm">
      <v-text-field
        v-model="firstName"
        label="First name"
        :rules="firstNameRules"
        outlined
        dense></v-text-field>

      <v-text-field
        v-model="lastName"
        label="Last name"
        :rules="lastNameRules"
        outlined
        dense></v-text-field>

      <v-select
        v-model="selectedItems"
        :items="items"
        chips
        label="Interests"
        multiple
        :rules="chipsRules"
        outlined
        dense></v-select>

      <v-btn type="submit" block class="mt-4" color="primary">Submit</v-btn>
    </v-form>
  </v-sheet>
</template>

<script setup>
import { ref } from "vue";

const firstName = ref("");
const lastName = ref("");
const selectedItems = ref([]);
const items = [
  "Option 1",
  "Option 2",
  "Option 3",
  "Option 4",
  "Option 5",
  "Option 6",
  "Option 7",
  "Option 8",
];

const firstNameRules = [
  (v) => !!v || "First name is required",
  (v) => v.length <= 20 || "First name must be less than 20 characters",
];

const lastNameRules = [
  (v) => !!v || "Last name is required",
  (v) => v.length <= 20 || "Last name must be less than 20 characters",
];

const chipsRules = [(v) => v.length > 0 || "Select at least one option"];
</script>

<style scoped>
.main-edit-profile-block {
  max-width: 400px;
  margin: 50% 10px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.v-select .v-chip {
  background-color: teal;
  color: white;
}

.v-btn {
  text-transform: uppercase;
  font-weight: bold;
}

.v-text-field,
.v-select {
  margin: 8px 0;
}
.main-edit-profile-block span {
  display: block;
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

</style>
