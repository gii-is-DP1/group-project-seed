import { Button } from "reactstrap";
import { Link } from "react-router-dom";
import useFetchState from "../hooks/useFetchState";
import * as achievementsApi from "../services/achievements";
import AchievementTable from "./AchievementTable";
import { useState } from "react";
import useErrorModal from "../hooks/useErrorModal";
import deleteFromList from "../util/deleteFromList";


export default function AchievementList() {
  const { errorModal, showError } = useErrorModal();
  const [achievements, setAchievements] = useFetchState(
    [], achievementsApi.getAllAchievements, [], { onError: showError }
  );
  const [alerts, setAlerts] = useState([]);

    function handleDelete(id) {
        deleteFromList(
            achievementsApi.deleteAchievement,
            id,
            [achievements, setAchievements],
            [alerts, setAlerts],
            { onError: showError }
        );
    }
    return (
    <div className="admin-page-container">
      <h1 className="text-center">Achievements</h1>
      {alerts.map((a) => a.alert)}
      {errorModal}
      <div>
        <AchievementTable achievements={achievements} onDelete={handleDelete} />
        <Button outline color="success">
          <Link
            to={`/achievements/new`}
            className="btn sm"
            style={{ textDecoration: "none" }}
          >
            Create achievement
          </Link>
        </Button>
      </div>
    </div>
  );
}

// import useFetchState from "../hooks/useFetchState";
// import * as achievementsApi from "../services/achievements";
// import AchievementTable from "./AchievementTable";

// export default function AchievementList() {
//   const [achievements, setAchievements] = useFetchState([],achievementsApi.getAllAchievements);
//   return (
//     <div className="admin-page-container">
//       <h1 className="text-center">Achievements</h1>
//       <div>
//         <AchievementTable achievements={achievements} />
//       </div>
//     </div>
//   );
// }

//import AchievementTable from "./AchievementTable";
//
// const achievements = [
//   {id:1, name:"Experiencia básica", description:"Si juegas 10 partidas",
//     badgeImage:"https://cdn-icons-png.flaticon.com/512/5243/5243423.png", 
//     threshold:"10", metric:"GAMES_PLAYED"},
//   {id:2, name:"Explorador", description:"Si juegas 25 partidas", 
//     badgeImage:"https://cdn-icons-png.flaticon.com/512/603/603855.png", 
//     threshold:"25", metric:"GAMES_PLAYED"},
//   {id:3, name:"Experto", description:"Si ganas 20 partidas", 
//     badgeImage:"https://cdn-icons-png.flaticon.com/512/4737/4737471.png",
//     threshold:"20", metric:"VICTORIES"}
// ];

// export default function AchievementList() {
//   return (
//     <div className="admin-page-container">
//       <h1 className="text-center">Achievements</h1>
//       <div>
//         <AchievementTable achievements={achievements} />
//       </div>
//     </div>
//   );
// }
