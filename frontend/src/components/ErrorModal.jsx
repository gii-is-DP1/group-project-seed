import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import PropTypes from "prop-types";

export default function ErrorModal({ visible, message, onClose }) {
    if (!message) return null;

    const closeBtn = (
        <button className="close" onClick={onClose} type="button">
            &times;
        </button>
    );

    return (
        <Modal isOpen={visible} toggle={onClose} keyboard={false}>
            <ModalHeader toggle={onClose} close={closeBtn}>Alert!</ModalHeader>
            <ModalBody>
                {message}
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={onClose}>Close</Button>
            </ModalFooter>
        </Modal>
    );
}

ErrorModal.propTypes = {
    visible: PropTypes.bool,
    message: PropTypes.string,
    onClose: PropTypes.func.isRequired,
};
