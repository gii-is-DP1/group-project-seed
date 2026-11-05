// Allure 3 configuration (detected automatically by allure-maven).
// It generates two reports from the same test results:
//   - awesome: grouped by @Epic > @Feature > @Story, which works as a
//     traceability matrix between modules, user stories and tests.
//   - layers: grouped by epic and feature, then by layer (@ControllerTest,
//     @ServiceTest...) and isolation (@IsolatedTest / @SociableTest).
// See the annotations in src/test/java/.../your_game_name/testing.
export default {
  plugins: {
    awesome: {
      options: {
        reportName: "Tests by epic / feature",
        groupBy: ["epic", "feature", "story"],
      },
    },
    layers: {
      import: "@allurereport/plugin-awesome",
      options: {
        reportName: "Tests by layer / isolation",
        singleFile: true,
        groupBy: ["epic", "feature","layer", "isolation"],
      },
    },
  },
};
