import { Table, Button } from "reactstrap"
import useFetchState from "../util/useFetchState";
import tokenService from "../services/token.service";
import { Link } from "react-router-dom"
import deleteFromList from "../util/deleteFromList";
import { useState } from "react";
import getErrorModal from "../util/getErrorModal";

const imageNotFound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png"
const jwt = tokenService.getLocalAccessToken()

/* const achievements = [{ id:1, name:"Experiencia básica", description:"Si juegas 10 partidas o más", badgeImage:"https://cdn-icons-png.flaticon.com/512/5243/5243423.png", threshold:"10", 
        metric:"GAMES_PLAYED"}, {id:2, name:"Explorador", description:"Si juegas 25 partidas o más", badgeImage:"https://cdn-icons-png.flaticon.com/512/603/603855.png", threshold:"25", 
            metric:"GAMES_PLAYED"}, {id:3, name:"Experto", description:"Si ganas 20 partidas o más", badgeImage:"https://cdn-icons-png.flaticon.com/512/4737/4737471.png", threshold:"20", 
                metric:"VICTORIES"}] */

export default function AchievementsList() {
    const [achievements, setAchievements] = useFetchState([], "/api/v1/achievements", jwt)
    const [alerts, setAlerts] = useState([])
    const [message, setMessage] = useState(null)
    const [visible, setVisible] = useState(false)
    const achievementsList = achievements.map((d) => { return (
        <tr key={d.id}>
            <td className="text-center">{d.name}</td>
            <td className="text-center"> {d.description} </td>
            <td className="text-center"> <img src={d.badgeImage ? d.badgeImage : imageNotFound} alt={d.name} width="50px"/> </td>
            <td className="text-center">{d.threshold}</td>
            <td className="text-center">{d.metric}</td>
            <td className="text-center">
                <Button outline color="warning" >
                    <Link to={`/achievements/` + d.id} className="btn sm" style={{ textDecoration: "none" }}>Edit</Link> 
                </Button>
                <Button outline color="danger" onClick={() => deleteFromList(`/api/v1/achievements/${d.id}`, d.id, [achievements, setAchievements], [alerts, setAlerts], setMessage, setVisible)}>
                    Delete
                </Button>
            </td>
        </tr>);});

    const modal = getErrorModal(setVisible, visible, message);

    return (
    <div>
        <div className="admin-page-container">
            <h1 className="text-center">Achievements</h1>
            {alerts.map((a) => a.alert)}
            {modal}
            <div>
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
                    <tbody>{achievementsList}</tbody>
                </Table>
                <Button outline color="success" >
                    <Link to={`/achievements/new`} className="btn sm" style={{ textDecoration: "none" }}>Create achievement</Link> 
                </Button>
            </div>
        </div>
    </div>
    )

}