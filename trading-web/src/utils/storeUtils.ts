import { useLocalDataStore } from "../stores/localDataStore";
import { usePropsStore } from "../stores/propertiesStore";
import { useUserStore } from "../stores/userStore";

export const resetAllStores = async() => {
    const stores = [
        usePropsStore(),
        useLocalDataStore(),
        useUserStore(),
    ];
    stores.forEach(store => store.$reset());
};