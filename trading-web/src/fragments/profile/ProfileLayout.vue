<template>
    <div class="text-white">
        <div class="flex border-b border-gray-line">
            <img class="w-[200px] h-[200px] m-10 aspect-square rounded-full object-cover
                cursor-pointer duration-200 active:scale-90"
                src="https://i.pinimg.com/1200x/d8/b6/d6/d8b6d659b9076c6bc75fe25673da9b66.jpg" alt="" />
            <div class="w-full py-5 px-7 border-l border-gray-line">
                <div class="mb-2 flex items-center">
                    <span class="grow font-medium text-3xl line-clamp-1">username</span>
                    <a-button class="!mr-1 !font-semibold" type="primary" @click="openUpdateForm = true"
                        v-if="!openUpdateForm">
                        Cập nhật
                    </a-button>
                    <a-button class="!mr-1 !font-semibold" type="primary" @click="startUpdateMyProfile()"
                        v-if="openUpdateForm">
                        Lưu
                    </a-button>
                    <a-button class="!mr-1 !font-semibold" type="primary" @click="openUpdateForm = false"
                        v-if="openUpdateForm">
                        Hủy
                    </a-button>
                    <SignOut />
                </div>

                <div v-if="!openUpdateForm" class="mb-2 flex items-center font-light text-soft-gray">
                    <FontAwesomeIcon class="mr-2" :icon="faEnvelope" />
                    <span class="line-clamp-1">example@mail.com</span>
                </div>
                <div v-if="!openUpdateForm" class="mb-2 flex items-center font-light text-soft-gray">
                    <FontAwesomeIcon class="mr-2" :icon="faLocationDot" />
                    <span class="line-clamp-1">Ha Noi, Viet Nam</span>
                </div>
                <div v-if="!openUpdateForm" class="mb-2 flex items-center font-light text-soft-gray">
                    <FontAwesomeIcon class="mr-2" :icon="faCakeCandles" />
                    <span class="line-clamp-1">15/09/2003</span>
                </div>
                <div v-if="!openUpdateForm" class="mb-2 flex items-center font-light text-soft-gray">
                    <FontAwesomeIcon class="mr-2" :icon="faUser" />
                    <span class="line-clamp-1">Male</span>
                </div>

                <div v-if="openUpdateForm" class="mb-2 flex items-center font-light text-soft-gray">
                    <span class="mr-2 shrink-0">Email</span>
                    <a-input placeholder="example@mail.com" />
                </div>
                <div v-if="openUpdateForm" class="mb-2 grid grid-cols-2 gap-5 font-light text-soft-gray">
                    <div class="basis-[50%] flex items-center">
                        <span class="mr-2 shrink-0">Quốc gia</span>
                        <a-dropdown class="grow">
                            <template #overlay>
                                <a-menu>
                                    <a-menu-item key="1">
                                        Nam
                                    </a-menu-item>
                                    <a-menu-item key="2">
                                        Nữ
                                    </a-menu-item>
                                    <a-menu-item key="3">
                                        Khác
                                    </a-menu-item>
                                </a-menu>
                            </template>
                            <a-button>
                                {{ "Lựa chọn" }}
                            </a-button>
                        </a-dropdown>
                    </div>
                    <div class="basis-[50%] flex items-center">
                        <span class="mr-2 shrink-0">Thành phố</span>
                        <a-dropdown class="grow">
                            <template #overlay>
                                <a-menu>
                                    <a-menu-item key="1">
                                        Nam
                                    </a-menu-item>
                                    <a-menu-item key="2">
                                        Nữ
                                    </a-menu-item>
                                    <a-menu-item key="3">
                                        Khác
                                    </a-menu-item>
                                </a-menu>
                            </template>
                            <a-button>
                                {{ "Lựa chọn" }}
                            </a-button>
                        </a-dropdown>
                    </div>
                </div>
                <div v-if="openUpdateForm" class="mb-2 flex items-center font-light text-soft-gray">
                    <span class="mr-2 shrink-0">Ngày sinh</span>
                    <a-date-picker v-model:value="myDateOfBirth" placeholder="dd/MM/yyyy" />
                </div>
                <div v-if="openUpdateForm" class="mb-2 flex items-center font-light text-soft-gray">
                    <span class="mr-2 shrink-0">Giới tính</span>
                    <a-dropdown>
                        <template #overlay>
                            <a-menu>
                                <a-menu-item key="1">
                                    Nam
                                </a-menu-item>
                                <a-menu-item key="2">
                                    Nữ
                                </a-menu-item>
                                <a-menu-item key="3">
                                    Khác
                                </a-menu-item>
                            </a-menu>
                        </template>
                        <a-button>
                            {{ "Lựa chọn" }}
                        </a-button>
                    </a-dropdown>
                </div>

                <div class="mt-6 p-5 bg-dark-area rounded-md">
                    <span class="block text-xl font-semibold">Giới thiệu</span>
                    <span v-if="!openUpdateForm" class="block">Hi</span>
                    <a-textarea
                        v-if="openUpdateForm"
                        v-model:value="myBio"
                        placeholder="Giới thiệu"
                        :rows="5"
                    />
                </div>
            </div>
        </div>

        <div>

        </div>
    </div>
</template>

<script setup lang="ts">
import { faCakeCandles, faEnvelope, faLocationDot, faUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import SignOut from '../auth/components/SignOut.vue';
import { ref } from 'vue';

const openUpdateForm = ref<boolean>(false);
const myDateOfBirth = ref(null);
const myBio = ref(null)

const startUpdateMyProfile = async() => {
    openUpdateForm.value = false;
}

</script>