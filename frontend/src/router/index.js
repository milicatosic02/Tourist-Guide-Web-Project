
import VueRouter from 'vue-router'
import PocetnaPage from "@/views/PocetnaPage.vue";
import Vue from "vue";

Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    name: 'PocetnaPage',
    component: PocetnaPage
   },
    {
    path: '/najcitanije',
    name: 'NajcitanijePage',
    component: () => import('../views/NajcitanijePage.vue')
  },
  {
    path: '/clanciZaDestinaciju/:id',
    name: 'ClanciZaDestinaciju',
    component: () => import('../views/ClanciZaDestinacijuPage.vue')
  },
  {
    path: '/clanciZaAktivnost/:id',
    name: 'ClanciZaAktivnost',
    component: () => import('../views/ClanciZaAktivnostPage.vue')
  },
  {
    path: '/izmeniClanak/:id',
    name: 'IzmeniClanakPage',
    component: () => import('../views/IzmeniClanakPage.vue')
  },
  {
    path: '/izmeniDestinaciju/:id',
    name: 'IzmeniDestinacijuPage',
    component: () => import('../views/IzmeniDestinacijuPage.vue')
  },
  {
    path: '/dodajDestinaciju',
    name: 'DodajDestinacijuPage',
    component: () => import('../views/DodajDestinacijuPage.vue')
  },
  {
    path: '/kreirajClanak',
    name: 'KreirajClanakPage',
    component: () => import('../views/KreirajClanak.vue')
  },
  {
    path: '/destinacije',
    name: 'DestinacijePage',
    component: () => import('../views/DestinacijePage.vue')
  },
    {
      path: '/clanci/:id',
      name: 'ClanakPage',
      component: () => import('../views/ClanakPage.vue')
    },
  {
    path: '/korisnici',
    name: 'KorisniciPage',
    component: () => import('../views/KorisniciPage.vue')
  },
  {
    path: '/izmeniKorisnika/:id',
    name: 'IzmeniKorisnikaPage',
    component: () => import('../views/IzmeniKorisnikaPage.vue')
  },
  {
    path: '/dodajKorisnika',
    name: 'KreirajKorisnikaPage',
    component: () => import('../views/KreirajKorisnikaPage.vue')
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: () => import('../views/LoginPage.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
