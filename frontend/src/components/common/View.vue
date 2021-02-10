<template>
  <div>
    <Welcome id="Home" />

    <about-us id="aboutus" />

    <recent-reviews id="reviews" />

    <contact-us id="contact" />

    <common-footer @gotolink="gotolink" />
  </div>
</template>

<script>
import AboutUs from '../../page/main/AboutUs.vue'
import RecentReviews from '../../page/main/RecentReviews.vue'
  export default {
    name: 'CommonView',

    components: {
      ContactUs: () => import('../../page/main/ContactUs'),
      RecentReviews: () => import('../../page/main/RecentReviews'),
      // Services: () => import('@/components/Services'),
      Welcome: () => import('../../page/main/Welcome'),
      CommonFooter: () => import('../common/Footer'),
      AboutUs: () => import('../../page/main/AboutUs'),
    },
    methods: {
      gotolink (linkitem) {
        this.$vuetify.goTo(`#${linkitem}`)
      }
    }
  }
</script>
