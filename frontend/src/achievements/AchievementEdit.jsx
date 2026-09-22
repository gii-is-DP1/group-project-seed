import { useNavigate, useParams } from "react-router-dom";
import useErrorModal from "../hooks/useErrorModal";
import useFetchState from "../hooks/useFetchState";
import * as achievementsApi from "../services/achievements";
import AchievementForm from "./AchievementForm";

export default function AchievementEdit() {
  const { id } = useParams();
  const emptyAchievement = {
    id: id === "new" ? null : id,
    name: "",
    description: "",
    badgeImage: "",
    threshold: 1,
    metric: "GAMES_PLAYED",
  };
  const navigate = useNavigate();
  const { errorModal, showError } = useErrorModal();
  const [achievement, setAchievement] = useFetchState(
    emptyAchievement,
    () => achievementsApi.getAchievementById(id),
    [id],
    { skip: id === "new", onError: showError }
  );

  function handleChange(event) {
    const { name, value } = event.target;
    setAchievement({ ...achievement, [name]: value });
  }

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      if (achievement.id) await achievementsApi.updateAchievement(achievement.id, achievement);
      else await achievementsApi.createAchievement(achievement);
      navigate("/achievements");
    } catch (err) {
      showError(err.response?.data?.message ?? "Error saving achievement");
    }
  }

  return (
    <>
      {errorModal}
      <AchievementForm achievement={achievement} onChange={handleChange} onSubmit={handleSubmit} />
    </>
  );
}
