import { Table } from "reactstrap";
import DeveloperRow from "./DeveloperRow";

export default function DeveloperList({ developers }) {
  return (
    <div className="admin-page-container">
      <h1 className="text-center">Developers</h1>
      <div>
        <Table aria-label="developers" className="mt-4">
          <thead>
            <tr>
              <th className="text-center">Name</th>
              <th className="text-center">e-mail</th>
              <th className="text-center">URL</th>
              <th className="text-center">Picture</th>
            </tr>
          </thead>
          <tbody>
            {developers.map((d) => <DeveloperRow key={d.id} developer={d} />)}
          </tbody>
        </Table>
      </div>
    </div>
  );
}
