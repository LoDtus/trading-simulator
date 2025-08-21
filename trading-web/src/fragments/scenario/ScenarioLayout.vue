<template>
    <div class="h-full p-5 flex text-white">
        <div class="basis-[50%] pr-5">
            <h2 class="!font-semibold text-2xl !mb-3">Các kịch bản</h2>

            <div class="w-full mb-2 flex items-center justify-end">
                <span>Sắp xếp</span>
                <!-- Theo tên, theo top, theo ngày tạo (xếp kiểu gì thì ghim vẫn được ghim, ghim vẫn giữ thứ tự, không đổi thứ tự ghim) -->
            </div>

            <ul class="grid grid-cols-2 gap-2">
                <button
                    class="ư-full h-full bg-dark-area font-semibold rounded-md
                    flex flex-col justify-center items-center
                    duration-200 hover:bg-soft-gray-hover active:scale-95"
                    @click="props.toggleAddScenarioModal"
                >
                    <div class="text-3xl">
                        <FontAwesomeIcon
                            :icon="faSquarePlus"
                        />
                    </div>
                    <span>Thêm kịch bản</span>
                </button>

                <li
                    v-for="(scenario, scenarioIndex) in scenarioList"
                    :key="scenarioIndex"
                    :class="[
                        'p-3 rounded-md bg-dark-area cursor-pointer duration-200 hover:bg-soft-gray-hover',
                        { 'outline-2 outline-yellow-500': selectedScenario === scenarioIndex }
                    ]"
                    @click="selectedScenario = scenarioIndex"
                >
                    <div class="flex items-center">
                        <FontAwesomeIcon :icon="faStar" class="text-yellow-500 mr-2 cursor-pointer duration-200 active:scale-90"/>
                        <div class="text-sm font-light px-3 mr-1 rounded-full !bg-red-500">Rank 1</div>
                        
                        <div class="grow"></div>
                        <a-popover
                            trigger="click"
                            placement="bottomRight"
                            @click.stop
                        >
                            <!-- :open="clicked"
                            @openChange="handleClickChange" -->
                            <template #content>
                                <div class="flex flex-col">
                                    <button
                                        class="!border py-1 px-5 rounded-sm
                                        duration-200 active:scale-90"
                                    >
                                        <FontAwesomeIcon
                                            :icon="faCircleInfo"
                                            class="mr-1"
                                        />
                                        <span>Chi tiết</span>
                                    </button>
                                    <button
                                        class="!border py-1 px-5 rounded-sm !mt-1
                                        duration-200 active:scale-90"
                                    >
                                        <FontAwesomeIcon
                                            :icon="faPenToSquare"
                                            class="mr-1"
                                        />
                                        <span>Cập nhật</span>
                                    </button>
                                    <button
                                        class="!border py-1 px-5 rounded-sm !mt-1
                                        duration-200 active:scale-90"
                                    >
                                        <FontAwesomeIcon
                                            :icon="faTrash"
                                            class="mr-1"
                                        />
                                        <span>Xóa</span>
                                    </button>
                                </div>
                            </template>
                            <button
                                class="w-[30px] h-[30px] aspect-square rounded-full flex justify-center items-center
                                duration-200 hover:bg-gray-hover active:scale-90"
                            >
                                <FontAwesomeIcon
                                    :icon="faEllipsis"
                                />
                            </button>
                        </a-popover>
                    </div>

                    <h3 class="grow !font-semibold !text-lg">Kịch bản {{ scenarioIndex }}</h3>
                    <p class="text-soft-gray line-clamp-3">Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptatibus cum
                        voluptatem non hic voluptatum culpa, repellat temporibus delectus? Ipsa voluptates quis odio?
                        Consequuntur ratione earum, velit voluptatibus rem dolor ut!
                    </p>

                    <a-button
                        type="primary"
                        class="w-full !font-semibold"
                        @click="router.push('/trade')"
                        @click.stop
                    >
                        Truy cập
                    </a-button>
                </li>
            </ul>
        </div>

        <div class="basis-[50%] h-full pl-5 border-l border-gray-line">
            <h2 class="!font-semibold text-2xl">Kịch bản 1</h2>
            <span class="text-white">{{ selectedScenario }}</span>
        </div>
    </div>
</template>

<script setup lang="ts">
import { faCircleInfo, faEllipsis, faPenToSquare, faStar, faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { usePropsStore } from '../../stores/propertiesStore';
import { faSquarePlus } from '@fortawesome/free-regular-svg-icons';

const router = useRouter();
const props = usePropsStore();
const scenarioList = [1,1,1,1,1,1,1];
const selectedScenario = ref<number | null>(null);
</script>