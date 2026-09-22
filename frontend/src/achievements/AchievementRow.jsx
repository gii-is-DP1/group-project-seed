import { Button } from "reactstrap";
import { Link } from "react-router-dom";

const imgNotFound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png";

export default function AchievementRow({ achievement, onDelete }) {
  return (
    <tr>
      <td className="text-center">{achievement.name}</td>
      <td className="text-center"> {achievement.description} </td>
      <td className="text-center">
        <img src={achievement.badgeImage ? achievement.badgeImage : imgNotFound} alt={achievement.name} width="50px"/>
      </td>
      <td className="text-center"> {achievement.threshold} </td>
      <td className="text-center"> {achievement.metric} </td>
      <td className="text-center">
        <Button outline color="warning">
          <Link
            to={`/achievements/${achievement.id}`}
            className="btn sm"
            style={{ textDecoration: "none" }}
          >
            Edit
          </Link>
        </Button>
        <Button outline color="danger" onClick={() => onDelete(achievement.id)}>
          Delete
        </Button>
      </td>
    </tr>
  );
}
