import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
    state: () => ({
        id: null as string | null,
        email: null as string | null,
        username: null as string | null,
        role: null as string | null,
        active: null as boolean | null,

        status: null as string | null,
        image: null as string | null,
        address: null as string[] | null,
        gender: null as string | null,
        dateOfBirth: null as string | null,

        rank: null as number | null,
    }),
    actions: {
        setUser(data: any) {
            this.id = data?.id;
            this.email = data?.email;
            this.username = data?.username;
            this.role = data?.role;
            this.active = data?.active;

            this.status = data?.status;
            this.image = data?.image;
            this.address = data?.address;
            this.gender = data?.gender;
            this.dateOfBirth = data?.dateOfBirth;

            this.rank = data?.rank;
        },
        updateUser<K extends keyof ReturnType<typeof useUserStore>["$state"]>(
            key: K,
            value: ReturnType<typeof useUserStore>["$state"][K]
        ) {
            (this as any)[key] = value;
        },

        // cách dùng: userStore.updateUser("active", true);
    },
});
