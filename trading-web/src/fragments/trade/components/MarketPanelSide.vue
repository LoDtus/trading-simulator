<template>
    <div class="basis-1/3 bg-dark-area rounded-sm">
        <div class="p-2 border-b border-gray-line">
            <div class="flex items-center border !border-gray rounded-md">
                <FontAwesomeIcon
                    :icon="faMagnifyingGlass"
                    class="pl-2 text-gray group-focus:text-white"
                />
                <a-input
                    class="!pl-2 !text-white placeholder:!text-gray"
                    placeholder="Tìm kiếm"
                    :bordered="false"
                    v-model:value="keyword"
                />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref } from 'vue';

const keyword = ref<string>('');
</script>