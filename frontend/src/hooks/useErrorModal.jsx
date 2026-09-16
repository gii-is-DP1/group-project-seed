import { useState } from "react";
import ErrorModal from "../components/ErrorModal";

/**
 * Owns the state for a single shared error modal so that several unrelated
 * operations within the same component (a fetch, a delete, a submit, ...)
 * can report errors through the same `showError` callback and have them
 * surface in one place.
 *
 * @returns {{errorModal: JSX.Element, showError: function(string): void}}
 *
 * @example
 * const { errorModal, showError } = useErrorModal();
 * const { data } = useFetchState([], usersApi.getAllUsers, [], { onError: showError });
 */
export default function useErrorModal() {
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);

    const showError = (msg) => {
        setMessage(msg);
        setVisible(true);
    };

    const errorModal = (
        <ErrorModal visible={visible} message={message} onClose={() => setVisible(false)} />
    );

    return { errorModal, showError };
}
