import {
    faHouse,
    faChartLine,
    faRankingStar,
    faClockRotateLeft,
    faGear,
    faBook,
    faMessage,
    faHeadphones,
    faCoins
} from "@fortawesome/free-solid-svg-icons";

// .env
export const API_URL = import.meta.env.VITE_API_URL;

export const navigationTabs = [
    { label: "Trang chủ", path: "/", icon: faHouse },
    { label: "Kịch bản", path: "/scenario", icon: faBook },
    { label: "Thị trường", path: "/market", icon: faChartLine },
    { label: "Giao dịch", path: "/trade", icon: faCoins },
    { label: "Xếp hạng", path: "/rank", icon: faRankingStar },
    { label: "Cài đặt", path: "/setting", icon: faGear },
    { label: "Hỗ trợ", path: "/support", icon: faHeadphones },
    { label: "Góp ý", path: "/feedback", icon: faMessage },
];