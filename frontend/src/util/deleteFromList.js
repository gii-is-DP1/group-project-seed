import getDeleteAlertsOrModal from "./getDeleteAlertsOrModal";


/**
 * Function to delete an item from a list via a service function call and update the state accordingly.
 * This function also handles alerts and modals based on the server's response.
 *
 * @param {function(string): Promise<any>} deleteFn - Service function used to delete the item, e.g. `usersApi.deleteUser`.
 * @param {string} id - The unique identifier of the item to be deleted.
 * @param {[Array, function]} state - An array containing the current state and the state setter function.
 * @param {[Array, function]} alerts - An array containing the current list of alerts and the state setter function for alerts.
 * @param {object} [options={}] - Optional parameters:
 *   - `date` {Date}: If provided, only items created before this date will be deleted from the state.
 *   - `filtered` {Array}: An optional filtered list that needs to be updated in addition to the main state.
 *   - `setFiltered` {function}: A function to update the `filtered` state if applicable.
 *   - `onError` {function(string): void}: Called with an error message on failure. Defaults to `window.alert`.
 *
 * @example
 * deleteFromList(usersApi.deleteUser, "123", [users, setUsers], [alerts, setAlerts], { onError: showError });
 */
export default async function deleteFromList(deleteFn, id, [state, setState], [alerts, setAlerts], options = {}) {
    const { onError = window.alert } = options;
    const confirmMessage = window.confirm("Are you sure you want to delete it?");
    if (!confirmMessage) return;

    try {
        const json = await deleteFn(id);

        if (options.date)
            setState(state.filter((i) => i.id !== id && i.creationDate < options.date));
        else if (options.filtered && options.setFiltered) {
            setState(state.filter((i) => i.id !== id));
            options.setFiltered(options.filtered.filter((i) => i.id !== id));
        }
        else
            setState(state.filter((i) => i.id !== id));

        if (json) getDeleteAlertsOrModal(json, id, alerts, setAlerts, onError);
    } catch (err) {
        console.log(err);
        onError("Error deleting entity");
    }
}
