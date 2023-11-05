<script setup>
import EventApi from "../../api/event.api";

const { boardEvent } = defineProps({
  boardEvent: Object,
  interestTitle: String,
});

function getMatchesCountTooltipText(matchesCount) {
  switch (matchesCount) {
    case 1:
      return "One matched co-worker wants to attend this";
    default:
      return `${matchesCount} matched co-workers want to attend this`;
  }
}

function attendEvent() {
  new Promise(EventApi.attendEvent(boardEvent.id)).then(() => {
    // Like an imbecile I am
    boardEvent.value = {
      ...boardEvent.value,
      accepted: true,
    };
  });
}

function skipEvent() {
  new Promise(EventApi.skipEvent(boardEvent.id)).then(() => {
    // Like an imbecile I am
    boardEvent.value = {
      ...boardEvent.value,
      accepted: false,
    };
  });
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
      <router-link :to="'/events/' + boardEvent.id">
        <v-btn> Open </v-btn>
      </router-link>
    </v-card-actions>
  </v-card>
</template>
