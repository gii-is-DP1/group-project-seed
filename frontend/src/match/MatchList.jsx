import { useState } from "react";
import { Table } from "reactstrap";
import * as matchesApi from "../services/matches";
import "../static/css/admin/adminPage.css";
import deleteFromList from "../util/deleteFromList";
import useErrorModal from "../hooks/useErrorModal";
import useFetchState from "../hooks/useFetchState";
import MatchRow from "./MatchRow";

export default function MatchList() {
  const { errorModal, showError } = useErrorModal();
  const [matches, setMatches] = useFetchState([], matchesApi.getAllMatches, [], {
    onError: showError,
  });
  const [alerts, setAlerts] = useState([]);

  return (
    <div className="admin-page-container">
      <h1 className="text-center">Matches</h1>
      {alerts.map((a) => a.alert)}
      {errorModal}
      <div>
        <Table aria-label="matches" className="mt-4">
          <thead>
            <tr>
              <th>Name</th>
              <th>Type</th>
              <th>Status</th>
              <th>Start</th>
              <th>Finish</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {matches.map((match) => (
              <MatchRow
                key={match.id}
                match={match}
                onDelete={() =>
                  deleteFromList(
                    matchesApi.deleteMatch,
                    match.id,
                    [matches, setMatches],
                    [alerts, setAlerts],
                    { onError: showError }
                  )
                }
              />
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}
