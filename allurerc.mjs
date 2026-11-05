// Allure 3 configuration (detected automatically by allure-maven).
// Groups the test tree by @Epic > @Feature > @Story, which works as a
// traceability matrix between modules, user stories and tests.
export default {
  plugins: {
    awesome: {
      options: {
        groupBy: ["epic", "feature", "story"],
      },
    },
  },
};
