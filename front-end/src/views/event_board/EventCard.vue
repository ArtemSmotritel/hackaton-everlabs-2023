<script setup>
import AppInterestChip from "../../components/AppInterestChip.vue";
import { useRouter } from "vue-router";

const { boardEvent } = defineProps({
  boardEvent: Object,
  interestTitle: String,
});
const router = useRouter();

function getMatchesCountTooltipText(matchesCount) {
  switch (matchesCount) {
    case 1:
      return "One matched co-worker wants to attend this";
    default:
      return `${matchesCount} matched co-workers want to attend this`;
  }
}

function openEventDetails() {
  console.log(boardEvent.id);
  // router.push(`/events/${boardEvent.id}`);
}

function attendEvent() {
  console.log(boardEvent.id);
}

function skipEvent() {
  console.log(boardEvent.id);
}
</script>

<template>
  <v-card class="mx-auto" max-width="344" variant="elevated">
    <v-card-title>
      <div class="text-overline mb-1 d-flex justify-space-between">
        <span>
          Attendants: {{ boardEvent.matchesCount }}
          <v-tooltip
            location="bottom"
            :text="getMatchesCountTooltipText(boardEvent.matchesCount ?? 0)"
          >
            <template v-slot:activator="{ props }">
              <v-icon
                icon="mdi-information"
                size="x-small"
                v-bind="props"
              ></v-icon>
            </template>
          </v-tooltip>
        </span>
        <span v-if="boardEvent.accepted" class="text-success"
          >including you</span
        >
      </div>
      {{ boardEvent.name }}
    </v-card-title>
    <v-card-subtitle>
      {{ boardEvent.date }}
    </v-card-subtitle>
    <v-card-text>
      <slot name="interests-title"></slot>
      <slot name="interests">
        <div class="font-weight-bold" style="height: 100px">
          No common interests &#129402; &#x1F97A;
        </div>
      </slot>
    </v-card-text>
    <v-card-actions>
      <v-btn color="success" v-if="!boardEvent.accepted" @click="attendEvent">
        Attend
      </v-btn>
      <v-btn @click="openEventDetails"> Open </v-btn>
    </v-card-actions>
  </v-card>
</template>
