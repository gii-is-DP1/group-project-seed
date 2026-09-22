import { Table } from "reactstrap";
import AchievementRow from "./AchievementRow";

export default function AchievementTable({ achievements, onDelete }) {
  return (
    <Table aria-label="achievements" className="mt-4">
      <thead>
        <tr>
          <th className="text-center">Name</th>
          <th className="text-center">Description</th>
          <th className="text-center">Image</th>
          <th className="text-center">Threshold</th>
          <th className="text-center">Metric</th>
          <th className="text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        {achievements.map((a) => <AchievementRow key={a.id} achievement={a} onDelete={onDelete} />)}
      </tbody>
    </Table>
  );
}
