<script setup>
import AppInterestChip from "../../components/AppInterestChip.vue";

defineProps({ event: Object });

function getMatchesCountTooltipText(matchesCount) {
  switch (matchesCount) {
    case 1:
      return "There is one person with some similar interests";
    default:
      return `There are ${matchesCount} people with some similar interests`;
  }
}
</script>

<template>
  <v-card class="mx-auto" max-width="344" variant="elevated" color="primary">
    <v-card-title>
      <div class="text-overline mb-1">
        Matches Count: {{ event.matchesCount }}
        <v-tooltip :text="getMatchesCountTooltipText(event.matchesCount ?? 0)">
          <template v-slot:activator="{ props }">
            <v-icon
              icon="mdi-information"
              size="x-small"
              v-bind="props"
            ></v-icon>
          </template>
        </v-tooltip>
      </div>
      {{ event.name }}
    </v-card-title>
    <v-card-subtitle>
      {{ event.date }}
    </v-card-subtitle>
    <v-card-text>
      <ul>
        <strong>Common Interests:</strong>
        <div>
          <AppInterestChip
            v-for="(interest, index) in event.commonInterests"
            :key="index"
            :text="interest.name"
            style="margin-bottom: 0 !important; margin-right: 0 !important;"
          />
        </div>
        <!-- <v-sheet class="overflow-auto" rounded>
          
        </v-sheet> -->
      </ul>
    </v-card-text>
    <v-card-actions>
      <v-btn> Attend </v-btn>
      <v-btn> Open </v-btn>
    </v-card-actions>
  </v-card>
</template>
