(() => {
    'use strict';

    const dialog = document.querySelector('#record-dialog');

    if (!dialog) {
        return;
    }

    dialog.addEventListener('click', event => {
        if (event.target.closest('[data-dialog-close]')) {
            dialog.close();
        }
    });
})();
