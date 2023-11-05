<script setup>
import { onMounted, ref } from "vue";
import AppInterestChip from "../../components/AppInterestChip.vue";
import EventApi from "../../api/event.api";
import EventCard from "./EventCard.vue";
import AppNavbar from "../../components/AppNavbar.vue";

const generalEvents = ref([]);
const privateEvents = ref([]);
const tab = ref(null);

onMounted(async () => {
  Promise.all([EventApi.getAllGeneral(), EventApi.getAllPrivate()]).then(
    ([publicResponse, privateResponse]) => {
      generalEvents.value = publicResponse.data;
      privateEvents.value = privateResponse.data;
    }
  );
});
</script>

<template>
  <AppNavbar />

  <v-container>
    <v-tabs v-model="tab" class="mx-6 my-2">
      <v-tab value="general" bg-color="red">General</v-tab>
      <v-tab value="private"
        ><v-icon start> mdi-lock-open-outline </v-icon>Private</v-tab
      >
    </v-tabs>

    <v-window v-model="tab" class="mt-12">
      <v-window-item value="general">
        <v-row>
          <v-col v-if="generalEvents.length <= 0" cols="12" md="6" lg="4">
            <p>Nothing here &#129402; &#x1F97A;</p>
          </v-col>
          <template v-else>
            <v-col
              v-for="event in generalEvents"
              :key="event.id"
              cols="12"
              md="6"
              lg="4"
            >
              <EventCard :board-event="event" interestTitle="Looking for">
                <template
                  v-if="event.interests?.length > 0"
                  v-slot:interests-title
                >
                  <strong>Looking for:</strong>
                </template>
                <template v-slot:interests>
                  <div
                    v-if="event.interests?.length > 0"
                    style="height: 80px; overflow-y: auto"
                  >
                    <AppInterestChip
                      v-for="(interest, index) in event.interests"
                      :key="index"
                      :text="interest.name"
                      style="
                        margin-bottom: 0 !important;
                        margin-right: 0 !important;
                      "
                    />
                  </div>
                </template>
              </EventCard>
            </v-col>
          </template>
        </v-row>
      </v-window-item>
      <v-window-item value="private">
        <v-row>
          <v-col v-if="privateEvents.length <= 0" cols="12" md="6" lg="4">
            <p>Nothing here &#129402; &#x1F97A;</p>
          </v-col>
          <template v-else>
            <v-col
              v-for="event in privateEvents"
              :key="event.id"
              cols="12"
              md="6"
              lg="4"
            >
              <EventCard :board-event="event" interestTitle="Looking for">
                <template
                  v-if="event.interests?.length > 0"
                  v-slot:interests-title
                >
                  <strong>Looking for:</strong>
                </template>
                <template v-slot:interests>
                  <div
                    v-if="event.interests?.length > 0"
                    style="height: 80px; overflow-y: auto"
                  >
                    <AppInterestChip
                      v-for="(interest, index) in event.interests"
                      :key="index"
                      :text="interest.name"
                      style="
                        margin-bottom: 0 !important;
                        margin-right: 0 !important;
                      "
                    />
                  </div>
                </template>
              </EventCard>
            </v-col>
          </template>
        </v-row>
      </v-window-item>
    </v-window>
  </v-container>
</template>

<style scoped></style>
