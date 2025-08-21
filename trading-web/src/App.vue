<template>
    <div class="w-full h-screen flex flex-col bg-dark-area">
        <HeaderSide/>

        <div
            class="w-full h-full flex"
        >
            <NavigationBar/>
            <div class="grow mx-2 bg-smoky-black border-2 rounded-lg border-gray-line overflow-y-auto">
                <router-view/>
            </div>
        </div>
        
        <FooterSide/>

        <AddScenarioModal v-if="props.openAddScenarioModal"/>
    </div>
</template>

<script setup lang="ts">
import NavigationBar from './fragments/shared-ui/NavigationBar.vue';
import HeaderSide from './fragments/shared-ui/HeaderSide.vue';
import FooterSide from './fragments/shared-ui/FooterSide.vue';
import { usePropsStore } from './stores/propertiesStore';
import AddScenarioModal from './fragments/scenario/components/AddScenarioModal.vue';

const props = usePropsStore();
</script>