import useFetchState from "../hooks/useFetchState";
import * as developersApi from "../services/developers";
import DeveloperList from "./DeveloperList";

export default function DeveloperContainer() {
  const [developers] = useFetchState([], developersApi.getAllDevelopers);
  return <DeveloperList developers={developers} />;
}

