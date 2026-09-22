const imgNotFound = "https://cdn-icons-png.flaticon.com/512/48/48639.png";

export default function DeveloperRow({ developer }) {
  const picUrl = developer.properties?.picUrl;

  return (
    <tr>
        <td className="text-center">{developer.name}</td>
        <td className="text-center"> {developer.email} </td>
        <td className="text-center"> <a href={developer.url}>{developer.url}</a> </td>
        <td className="text-center">
            <img src={picUrl ? picUrl : imgNotFound} alt={developer.name} width="50px"/>
        </td>
    </tr>
  );
}


