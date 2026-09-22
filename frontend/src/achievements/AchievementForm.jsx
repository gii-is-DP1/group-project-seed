import { Form } from "reactstrap";
import { Link } from "react-router-dom";
import FormTextField from "../components/formFields/FormTextField";
import FormSelectField from "../components/formFields/FormSelectField";

const METRIC_OPTIONS = [
  { value: "GAMES_PLAYED", label: "GAMES_PLAYED" },
  { value: "VICTORIES", label: "VICTORIES" },
  { value: "TOTAL_PLAY_TIME", label: "TOTAL_PLAY_TIME" },
];

export default function AchievementForm({ achievement, onChange, onSubmit }) {
  return (
    <div className="auth-page-container">
      <h2 className="text-center">
        {achievement.id ? "Edit Achievement" : "Add Achievement"}
      </h2>
      <div className="auth-form-container">
        <Form onSubmit={onSubmit}>
          <FormTextField name="name" label="Name" value={achievement.name} onChange={onChange} />
          <FormTextField name="description" label="Description" value={achievement.description} onChange={onChange} />
          <FormTextField name="badgeImage" label="Badge Image Url:" value={achievement.badgeImage} onChange={onChange} />
          <FormSelectField name="metric" label="Metric" value={achievement.metric} onChange={onChange} options={METRIC_OPTIONS} />
          <FormTextField name="threshold" label="Threshold value:" type="number" value={achievement.threshold} onChange={onChange} />
          <div className="custom-button-row">
            <button className="auth-button">Save</button>
            <Link to={`/achievements`} className="auth-button" style={{ textDecoration: "none" }}>Cancel</Link>
          </div>
        </Form>
      </div>
    </div>
  );
}
