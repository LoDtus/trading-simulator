import { createRouter, createWebHistory } from "vue-router";
import ProfileLayout from "../fragments/profile/ProfileLayout.vue";
import SettingLayout from "../fragments/setting/SettingLayout.vue";
import RankLayout from "../fragments/rank/RankLayout.vue";
import TradeLayout from "../fragments/trade/TradeLayout.vue";
import ScenarioLayout from "../fragments/scenario/ScenarioLayout.vue";
import AuthLayout from "../fragments/auth/AuthLayout.vue";
import NoAccess from "../fragments/auth/components/NoAccess.vue";
import SignIn from "../fragments/auth/components/SignIn.vue";
import SignUp from "../fragments/auth/components/SignUp.vue";
import ForgotPassword from "../fragments/auth/components/ForgotPassword.vue";
import ResetPassword from "../fragments/auth/components/ResetPassword.vue";
import FeedbackLayout from "../fragments/feedback/FeedbackLayout.vue";
import SupportLayout from "../fragments/support/SupportLayout.vue";
import AdminLayout from "../fragments/admin/AdminLayout.vue";
import UserManagementLayout from "../fragments/user-management/UserManagementLayout.vue";
import NotificationLayout from "../fragments/notification/NotificationLayout.vue";
import PublicQuestionLayout from "../fragments/support/PublicQuestionLayout.vue";
import AskLayout from "../fragments/support/AskLayout.vue";
import MyQuestionDetails from "../fragments/support/components/MyQuestionDetails.vue";
import ToggleModeQuestion from "../fragments/support/components/ToggleModeQuestion.vue";
import MarketLayout from "../fragments/market/MarketLayout.vue";
import FavoriteTab from "../fragments/market/components/FavoriteTab.vue";
import CryptoTab from "../fragments/market/components/CryptoTab.vue";
import FutureTab from "../fragments/market/components/FutureTab.vue";
import ConvertTab from "../fragments/trade/tabs/ConvertTab.vue";
import MarketSpotTab from "../fragments/market/components/MarketSpotTab.vue";
import TradeSpotTab from "../fragments/trade/tabs/TradeSpotTab.vue";

const routes = [
    { path: "/auth", name: "Auth", component: AuthLayout,
        children: [
            { path: "", redirect: "/auth/sign-in" },
            { path: "sign-in", name: "auth-sign-in", component: SignIn },
            { path: "sign-up", name: "auth-sign-up", component: SignUp },
            { path: "forgot-password", name: "auth-forgot-password", component: ForgotPassword },
            { path: "reset-password", name: "auth-reset-password", component: ResetPassword },
            { path: "no-access", name: "auth-no-access", component: NoAccess },
        ]
    },

    { path: "/scenario", name: "scenario", component: ScenarioLayout },
    { path: "/scenario/:id", name: "scenario-details", component: ScenarioLayout },

    { path: "/market", name: "market", component: MarketLayout,
        children: [
            { path: "", redirect: "/market/favorite" },
            { path: "favorite", name: "market-favorite", component: FavoriteTab },
            { path: "crypto", name: "market-crypto", component: CryptoTab },
            { path: "spot", name: "market-spot", component: MarketSpotTab },
            { path: "future", name: "market-future", component: FutureTab },
        ]
    },
    { path: "/trade", name: "trade", component: TradeLayout,
        children: [
            { path: "", redirect: "/trade/spot" },
            { path: "spot", name: "trade-spot", component: TradeSpotTab },
            { path: "convert", name: "trade-convert", component: ConvertTab },
        ]
    },

    { path: "/rank", name: "rank", component: RankLayout },
    { path: "/rank/:id", name: "rank-details", component: RankLayout },

    { path: "/setting", name: "setting", component: SettingLayout },

    { path: "/support", name: "support", component: SupportLayout },
    { path: "/support/question", name: "support-question", component: SupportLayout },
    { path: "/support/question/:slug", name: "support-question-details", component: PublicQuestionLayout },
    { path: "/support/ask", name: "support-ask", component: AskLayout,
        children: [
            { path: "", name: "support-ask-toggle", component: ToggleModeQuestion },
            { path: ":id", name: "support-ask-by-id", component: MyQuestionDetails },
        ]
    },

    { path: "/feedback", name: "feedback", component: FeedbackLayout },

    { path: "/profile", name: "my-profile", component: ProfileLayout },
    { path: "/profile/:id", name: "profile-details", component: ProfileLayout },

    { path: "/admin", name: "admin", component: AdminLayout,
        children: [
            { path: "/user-management", name: "admin-user-management", component: UserManagementLayout },
            { path: "/scenario-management", name: "admin-scenario-management", component: UserManagementLayout },
            { path: "/notification", name: "admin-notification-management", component: NotificationLayout },
            { path: "/support", name: "admin-support", component: UserManagementLayout },
        ]
    },
];

const appRouter = createRouter({
    history: createWebHistory(),
    routes,
});

export default appRouter;