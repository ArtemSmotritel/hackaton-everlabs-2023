<script  setup>
defineProps({
  text: {
    type: String,
    required: true,
  },
  salt: {
    type: Number,
    required: false,
  },
});

const getChipColor = (salt) => {
  const allColors = ["success", "purple", "orange", "blue"];

  if (!salt) {
    salt = Math.floor(Math.random() * 100);
  }

  const randomOffset = salt % allColors.length;

  return allColors[randomOffset];
};
</script>

<template>
  <v-chip class="ma-2" :color="getChipColor(salt)" variant="tonal">
    {{ text }}
  </v-chip>
</template>

<style scoped></style>
