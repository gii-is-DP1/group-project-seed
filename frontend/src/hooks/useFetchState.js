import { useEffect, useState } from "react";

/**
 * Custom React hook to call a backend service function and manage the result within state.
 * Errors (any rejected request, e.g. a non-2xx response) are reported through `onError`,
 * defaulting to `window.alert` when the caller doesn't need a shared error channel.
 *
 * @param {any} initial - The initial value for the `data` state.
 * @param {function(): Promise<any>} fetcher - Service function to call, e.g. `usersApi.getAllUsers`
 *   or `() => usersApi.getUserById(id)`. Not called if `skip` is true.
 * @param {Array} [deps=[]] - Dependency array controlling when `fetcher` is re-run, mirroring `useEffect`'s deps.
 * @param {object} [options={}]
 * @param {boolean} [options.skip=false] - If true, the fetch is not performed (e.g. a "new entity" form).
 * @param {function(string): void} [options.onError] - Called with the error message on failure.
 *   Defaults to `window.alert`.
 *
 * @returns {[any, function]} - `[data, setData]`, mirroring `useState`.
 *
 * @example
 * const [users, setUsers] = useFetchState([], usersApi.getAllUsers, [], { onError: showError });
 * const [user, setUser] = useFetchState(emptyItem, () => usersApi.getUserById(id), [id], { skip: id === "new", onError: showError });
 */
export default function useFetchState(initial, fetcher, deps = [], options = {}) {
    const { skip = false, onError = window.alert } = options;
    const [data, setData] = useState(initial);

    useEffect(() => {
        if (skip) return;

        let ignore = false;

        async function load() {
            try {
                const result = await fetcher();
                if (!ignore) setData(result);
            } catch (err) {
                if (!ignore) {
                    console.log(err);
                    onError(err.response?.data?.message ?? "Failed to fetch data");
                }
            }
        }

        load();

        return () => {
            ignore = true;
        };
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [skip, ...deps]);

    return [data, setData];
}
