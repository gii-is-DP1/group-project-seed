import { Table, Button } from "reactstrap"
import useFetchState from "../util/useFetchState";
import tokenService from "../services/token.service";
import { Link } from "react-router-dom"
import { useNavigate } from "react-router-dom";

const imageNotFound = "https://cdn-icons-png.flaticon.com/512/5778/5778223.png"
const jwt = tokenService.getLocalAccessToken()
/* const achievements = [{ id:1, name:"Experiencia básica", description:"Si juegas 10 partidas o más", badgeImage:"https://cdn-icons-png.flaticon.com/512/5243/5243423.png", threshold:"10", 
        metric:"GAMES_PLAYED"}, {id:2, name:"Explorador", description:"Si juegas 25 partidas o más", badgeImage:"https://cdn-icons-png.flaticon.com/512/603/603855.png", threshold:"25", 
            metric:"GAMES_PLAYED"}, {id:3, name:"Experto", description:"Si ganas 20 partidas o más", badgeImage:"https://cdn-icons-png.flaticon.com/512/4737/4737471.png", threshold:"20", 
                metric:"VICTORIES"}] */

export default function AchievementsList() {
    const [achievements, setAchievements] = useFetchState([], "/api/v1/achievements", jwt)
    const achievementsList = achievements.map((d) => { return (
        <tr key={d.id}>
            <td className="text-center">{d.name}</td>
            <td className="text-center"> {d.description} </td>
            <td className="text-center"> <img src={d.badgeImage ? d.badgeImage : imageNotFound} alt={d.name} width="50px"/> </td>
            <td className="text-center">{d.threshold}</td>
            <td className="text-center">{d.metric}</td>
            <td className="text-center">---</td>
        </tr>);});

    return (
    <div>
        <div className="admin-page-container">
            <h1 className="text-center">Achievements</h1>
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