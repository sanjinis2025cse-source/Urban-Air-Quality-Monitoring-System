(() => {
    'use strict';

    const endpoints = {
        stations: '/api/stations',
        'air-quality': '/api/air-quality',
        industries: '/api/industries',
        emissions: '/api/emissions',
        limits: '/api/emission-limits',
        violations: '/api/violations',
        fines: '/api/fines',
        payments: '/api/payments'
    };

    const labels = {
        dashboard: 'Overview',
        'air-quality': 'Air quality',
        stations: 'Monitoring stations',
        industries: 'Industries',
        emissions: 'Emission records',
        limits: 'Emission limits',
        violations: 'Violations',
        fines: 'Fines',
        payments: 'Payments'
    };

    const descriptions = {
        'air-quality': 'Track recent readings and look up live conditions for a city.',
        stations: 'Manage the monitoring network and station locations.',
        industries: 'Manage registered industrial facilities and contacts.',
        emissions: 'Review reported pollutant measurements and compliance status.',
        limits: 'Set permitted concentration values for regulated pollutants.',
        violations: 'Review exceedances, severity and resolution status.',
        fines: 'Manage regulatory fines and outstanding balances.',
        payments: 'Review transactions and process fine payments.'
    };

    const configs = {

        stations: {
            title: 'Monitoring stations',

            fields: [
                ['stationName', 'Station name', 'text'],
                ['location', 'Location', 'text'],
                ['city', 'City', 'text'],
                ['status', 'Status', 'select', ['ACTIVE', 'INACTIVE']]
            ],

            cols: [
                ['stationName', 'Station'],
                ['city', 'City'],
                ['location', 'Location'],
                ['status', 'Status']
            ]
        },

        industries: {
            title: 'Industries',

            fields: [
                ['industryName', 'Industry name', 'text'],
                ['industryType', 'Industry type', 'text'],
                ['location', 'Location', 'text'],
                ['contactPerson', 'Contact person', 'text'],
                ['contactNumber', 'Contact number', 'text'],
                ['status', 'Status', 'select', ['ACTIVE', 'INACTIVE']]
            ],

            cols: [
                ['industryName', 'Industry'],
                ['industryType', 'Type'],
                ['location', 'Location'],
                ['contactPerson', 'Contact'],
                ['contactNumber', 'Phone'],
                ['status', 'Status']
            ]
        },

        'air-quality': {
            title: 'Air quality readings',

            fields: [
                ['city', 'City', 'text'],
                ['latitude', 'Latitude', 'number'],
                ['longitude', 'Longitude', 'number'],
                ['pm25', 'PM2.5', 'number'],
                ['pm10', 'PM10', 'number'],
                ['carbonMonoxide', 'Carbon monoxide', 'number'],
                ['nitrogenDioxide', 'Nitrogen dioxide', 'number'],
                ['sulphurDioxide', 'Sulphur dioxide', 'number'],
                ['ozone', 'Ozone', 'number'],
                ['aqi', 'AQI', 'number'],
                ['airQualityStatus', 'Air quality status', 'text'],
                ['recordedAt', 'Recorded at', 'datetime-local']
            ],

            cols: [
                ['city', 'City'],
                ['aqi', 'AQI'],
                ['airQualityStatus', 'Status'],
                ['pm25', 'PM2.5'],
                ['pm10', 'PM10'],
                ['carbonMonoxide', 'CO'],
                ['nitrogenDioxide', 'NO₂'],
                ['sulphurDioxide', 'SO₂'],
                ['ozone', 'O₃'],
                ['recordedAt', 'Recorded']
            ]
        },

        emissions: {
            title: 'Emission records',

            fields: [
                ['industry', 'Industry', 'relation', 'industries'],
                ['pm25', 'PM2.5', 'number'],
                ['pm10', 'PM10', 'number'],
                ['carbonMonoxide', 'Carbon monoxide', 'number'],
                ['nitrogenDioxide', 'Nitrogen dioxide', 'number'],
                ['sulphurDioxide', 'Sulphur dioxide', 'number'],
                ['ozone', 'Ozone', 'number'],
                [
                    'complianceStatus',
                    'Compliance status',
                    'select',
                    ['COMPLIANT', 'NON_COMPLIANT', 'PENDING']
                ],
                ['recordedAt', 'Recorded at', 'datetime-local']
            ],

            cols: [
                ['industry', 'Industry'],
                ['pm25', 'PM2.5'],
                ['pm10', 'PM10'],
                ['carbonMonoxide', 'CO'],
                ['nitrogenDioxide', 'NO₂'],
                ['sulphurDioxide', 'SO₂'],
                ['ozone', 'O₃'],
                ['complianceStatus', 'Compliance'],
                ['recordedAt', 'Recorded']
            ]
        },

        limits: {
            title: 'Emission limits',

            fields: [
                ['pollutant', 'Pollutant', 'text'],
                ['permittedValue', 'Permitted value', 'number'],
                ['unit', 'Unit', 'text']
            ],

            cols: [
                ['pollutant', 'Pollutant'],
                ['permittedValue', 'Permitted value'],
                ['unit', 'Unit']
            ]
        },

        violations: {
            title: 'Violations',

            fields: [
                ['industry', 'Industry', 'relation', 'industries'],
                ['emissionRecord', 'Emission record', 'relation', 'emissions'],
                ['pollutant', 'Pollutant', 'text'],
                ['permittedValue', 'Permitted value', 'number'],
                ['actualValue', 'Actual value', 'number'],
                ['exceedance', 'Exceedance', 'number'],
                [
                    'severity',
                    'Severity',
                    'select',
                    ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL']
                ],
                ['description', 'Description', 'text'],
                [
                    'status',
                    'Status',
                    'select',
                    ['OPEN', 'UNDER_REVIEW', 'RESOLVED']
                ],
                ['detectedAt', 'Detected at', 'datetime-local']
            ],

            cols: [
                ['industry', 'Industry'],
                ['pollutant', 'Pollutant'],
                ['permittedValue', 'Permitted'],
                ['actualValue', 'Actual'],
                ['exceedance', 'Exceedance'],
                ['severity', 'Severity'],
                ['detectedAt', 'Detected'],
                ['status', 'Status']
            ]
        },

        fines: {
            title: 'Fines',

            fields: [
                ['violation', 'Violation', 'relation', 'violations'],
                ['industry', 'Industry', 'relation', 'industries'],
                ['fineAmount', 'Fine amount', 'number'],
                ['issueDate', 'Issue date', 'date'],
                ['dueDate', 'Due date', 'date'],
                [
                    'paymentStatus',
                    'Payment status',
                    'select',
                    ['UNPAID', 'PAID', 'OVERDUE']
                ]
            ],

            cols: [
                ['industry', 'Industry'],
                ['violation', 'Violation'],
                ['fineAmount', 'Fine amount'],
                ['issueDate', 'Issued'],
                ['dueDate', 'Due'],
                ['paymentStatus', 'Payment status']
            ]
        },

        payments: {
            title: 'Payments',

            fields: [
                ['fine', 'Fine', 'relation', 'fines'],
                ['amount', 'Amount', 'number'],
                ['paymentDate', 'Payment date', 'datetime-local'],
                [
                    'paymentMethod',
                    'Payment method',
                    'select',
                    ['CARD', 'BANK_TRANSFER', 'CASH', 'UPI']
                ],
                ['transactionReference', 'Transaction reference', 'text']
            ],

            cols: [
                ['fine', 'Fine'],
                ['amount', 'Amount'],
                ['paymentDate', 'Payment date'],
                ['paymentMethod', 'Method'],
                ['transactionReference', 'Reference']
            ]
        }
    };

    const cache = {};

    let page = 'dashboard';
    let editing = null;
    let toastTimer;

    const $ = (selector, root = document) =>
        root.querySelector(selector);

    const $$ = (selector, root = document) =>
        [...root.querySelectorAll(selector)];

    const safe = value =>
        String(value ?? '—').replace(
            /[&<>"']/g,
            c => ({
                '&': '&amp;',
                '<': '&lt;',
                '>': '&gt;',
                '"': '&quot;',
                "'": '&#39;'
            }[c])
        );

    async function api(url, options = {}) {

        const response = await fetch(url, {
            ...options,
            headers: {
                ...(options.body
                    ? { 'Content-Type': 'application/json' }
                    : {}),
                ...(options.headers || {})
            }
        });

        if (!response.ok) {

            let detail = '';
            try {
                const body = await response.json();
                detail = body.message || '';
            } catch (_) {
                // Some non-live endpoints may still return an empty body.
            }

            if (response.status === 404) {
                throw new Error(detail || 'Record not found');
            }

            throw new Error(detail || `Request failed (${response.status})`);
        }

        if (response.status === 204) {
            return null;
        }

        const type =
            response.headers.get('content-type') || '';

        return type.includes('json')
            ? response.json()
            : null;
    }

    function notify(message, error = false) {

        const element = $('#toast');

        if (!element) {
            return;
        }

        element.textContent = message;

        element.classList.toggle('error', error);

        element.classList.add('show');

        clearTimeout(toastTimer);

        toastTimer = setTimeout(() => {
            element.classList.remove('show');
        }, 3200);
    }

    function display(value, key) {

        if (value && typeof value === 'object') {

            if (key === 'industry') {
                return value.industryName ||
                    `Industry #${value.id}`;
            }

            if (key === 'monitoringStation') {
                return value.stationName ||
                    `Station #${value.id}`;
            }

            if (key === 'fine') {
                return `Fine #${value.id}`;
            }

            if (key === 'violation') {
                return `Violation #${value.id}`;
            }

            if (key === 'emissionRecord') {
                return `Record #${value.id}`;
            }

            return value.id
                ? `#${value.id}`
                : '—';
        }

        if (value == null || value === '') {
            return '—';
        }

        if (typeof value === 'number') {
            return Number(value).toLocaleString(
                undefined,
                {
                    maximumFractionDigits: 2
                }
            );
        }

        return String(value)
            .replace('T', ' ')
            .replace('Z', '');
    }

    function pill(value) {

        const val =
            String(value ?? '')
                .toLowerCase()
                .replaceAll(' ', '-');

        return `
            <span class="pill ${safe(val)}">
                ${safe(value || '—')}
            </span>
        `;
    }

    function cell(row, key) {

        const value = row?.[key];

        if (
            [
                'status',
                'paymentStatus',
                'complianceStatus',
                'severity',
                'airQualityStatus'
            ].includes(key)
        ) {
            return pill(value);
        }

        if (
            key === 'fineAmount' ||
            key === 'amount'
        ) {
            return value == null
                ? '—'
                : `₹${display(value, key)}`;
        }

        return safe(display(value, key));
    }

    function actionCell(row, resource) {

        let buttons = `
            <button
                class="small-action"
                data-edit="${row.id}">
                Edit
            </button>

            <button
                class="small-action"
                data-delete="${row.id}">
                Delete
            </button>
        `;

        if (resource === 'emissions') {

            buttons += `
                <button
                    class="small-action"
                    data-detect="${row.id}">
                    Detect
                </button>
            `;
        }

        if (
            resource === 'violations' &&
            !cache.fines?.some(
                fine => fine.violation?.id === row.id
            )
        ) {

            buttons += `
                <button
                    class="small-action"
                    data-generate="${row.id}">
                    Generate fine
                </button>
            `;
        }

        if (
            resource === 'fines' &&
            String(row.paymentStatus).toUpperCase() !== 'PAID' &&
            !cache.payments?.some(
                payment => payment.fine?.id === row.id
            )
        ) {

            buttons += `
                <button
                    class="small-action"
                    data-pay="${row.id}">
                    Pay
                </button>
            `;
        }

        return `
            <div class="row-actions">
                ${buttons}
            </div>
        `;
    }

    async function load(resource) {

        cache[resource] =
            await api(endpoints[resource]);

        return cache[resource] || [];
    }

    async function refreshAll() {

        try {

            await Promise.all(
                Object.keys(endpoints).map(load)
            );

            renderDashboard();

            if (page !== 'dashboard') {
                renderTable(page);
            }

            notify('Workspace data refreshed');

        } catch (error) {

            notify(
                `Unable to load data: ${error.message}`,
                true
            );

            renderDashboard();
        }
    }

    function renderDashboard() {

        const readings =
            cache['air-quality'] || [];

        const reading =
            [...readings]
                .sort(
                    (a, b) =>
                        String(b.recordedAt || '')
                            .localeCompare(
                                String(a.recordedAt || '')
                            )
                )[0];

        const values = {

            aqi: reading?.aqi,

            pm25: reading?.pm25,

            pm10: reading?.pm10,

            co: reading?.carbonMonoxide,

            no2: reading?.nitrogenDioxide,

            so2: reading?.sulphurDioxide,

            o3: reading?.ozone
        };

        for (
            const [key, value]
            of Object.entries(values)
        ) {

            const element =
                $(`#metric-${key}`);

            if (element) {
                element.textContent =
                    display(value);
            }
        }

        const status =
            reading?.airQualityStatus ||
            'Awaiting data';

        const statusElement =
            $('#aqi-status');

        if (statusElement) {

            statusElement.textContent =
                status;

            statusElement.className =
                `status-badge ${
                    String(status)
                        .toLowerCase()
                        .includes('good')
                        ? 'good'
                        : 'neutral'
                }`;
        }

        /*
         * FIX:
         *
         * HTML contains:
         *
         * <span id="aqi-track"></span>
         *
         * Therefore we must select #aqi-track directly.
         *
         * The old code used:
         *
         * $('#aqi-track span')
         *
         * which returned null.
         */

        const aqiTrack =
            $('#aqi-track');

        if (aqiTrack) {

            const aqiPercentage =
                Math.min(
                    98,
                    Math.max(
                        0,
                        (Number(reading?.aqi) || 0)
                        / 500
                        * 100
                    )
                );

            aqiTrack.style.left =
                `${aqiPercentage}%`;
        }

        drawChart(values);

        const industries =
            cache.industries || [];

        const violations =
            cache.violations || [];

        const fines =
            cache.fines || [];

        const industryTotal =
            $('#industry-total');

        if (industryTotal) {
            industryTotal.textContent =
                industries.length;
        }

        const openViolations =
            violations.filter(
                violation =>
                    ![
                        'RESOLVED',
                        'CLOSED'
                    ].includes(
                        String(
                            violation.status
                        ).toUpperCase()
                    )
            );

        const openViolationElement =
            $('#open-violations');

        if (openViolationElement) {
            openViolationElement.textContent =
                openViolations.length;
        }

        const violationCount =
            $('#violation-count');

        if (violationCount) {
            violationCount.textContent =
                openViolations.length;
        }

        const total =
            fines.reduce(
                (sum, fine) =>
                    sum +
                    (Number(fine.fineAmount) || 0),
                0
            );

        const paid =
            fines.filter(
                fine =>
                    String(
                        fine.paymentStatus
                    ).toUpperCase() === 'PAID'
            );

        const unpaid =
            fines.filter(
                fine =>
                    String(
                        fine.paymentStatus
                    ).toUpperCase() !== 'PAID'
            );

        const paidTotal =
            paid.reduce(
                (sum, fine) =>
                    sum +
                    (Number(fine.fineAmount) || 0),
                0
            );

        const unpaidTotal =
            unpaid.reduce(
                (sum, fine) =>
                    sum +
                    (Number(fine.fineAmount) || 0),
                0
            );

        const fineTotal =
            $('#fine-total');

        if (fineTotal) {
            fineTotal.textContent =
                `₹${total.toLocaleString(
                    undefined,
                    {
                        maximumFractionDigits: 0
                    }
                )}`;
        }

        const paidFines =
            $('#paid-fines');

        if (paidFines) {
            paidFines.textContent =
                `₹${paidTotal.toLocaleString(
                    undefined,
                    {
                        maximumFractionDigits: 0
                    }
                )}`;
        }

        const unpaidFines =
            $('#unpaid-fines');

        if (unpaidFines) {
            unpaidFines.textContent =
                `₹${unpaidTotal.toLocaleString(
                    undefined,
                    {
                        maximumFractionDigits: 0
                    }
                )}`;
        }

        const fineProgress =
            $('#fine-progress');

        if (fineProgress) {

            fineProgress.style.width =
                total
                    ? `${paidTotal / total * 100}%`
                    : '0%';
        }

        const recent =
            [...violations]
                .sort(
                    (a, b) =>
                        String(b.detectedAt || '')
                            .localeCompare(
                                String(a.detectedAt || '')
                            )
                )
                .slice(0, 5);

        const recentViolations =
            $('#recent-violations');

        if (recentViolations) {

            recentViolations.innerHTML =
                recent.length

                    ? recent.map(
                        violation => `
                            <tr>
                                <td class="industry-cell">
                                    ${safe(
                                        display(
                                            violation.industry,
                                            'industry'
                                        )
                                    )}
                                </td>

                                <td>
                                    ${safe(
                                        violation.pollutant
                                    )}
                                </td>

                                <td>
                                    ${display(
                                        violation.exceedance
                                    )}
                                </td>

                                <td>
                                    ${pill(
                                        violation.severity
                                    )}
                                </td>

                                <td>
                                    ${safe(
                                        display(
                                            violation.detectedAt
                                        )
                                    )}
                                </td>

                                <td>
                                    ${pill(
                                        violation.status
                                    )}
                                </td>
                            </tr>
                        `
                    ).join('')

                    : `
                        <tr>
                            <td
                                colspan="6"
                                class="empty-cell">
                                No violation records found
                            </td>
                        </tr>
                    `;
        }
    }

    function drawChart(values) {

        const items = [
            ['PM2.5', values.pm25],
            ['PM10', values.pm10],
            ['CO', values.co],
            ['NO₂', values.no2],
            ['SO₂', values.so2],
            ['O₃', values.o3]
        ].filter(
            item => item[1] != null
        );

        const chart =
            $('#pollutant-chart');

        if (!chart) {
            return;
        }

        if (!items.length) {

            chart.innerHTML = `
                <div class="chart-empty">
                    Air quality readings will appear here
                </div>
            `;

            return;
        }

        const max =
            Math.max(
                ...items.map(
                    item =>
                        Number(item[1]) || 0
                ),
                1
            );

        chart.innerHTML =
            items.map(
                ([name, value]) => `
                    <div class="bar-group">

                        <span class="bar-value">
                            ${safe(display(value))}
                        </span>

                        <div
                            class="bar"
                            style="height:${Math.max(
                                5,
                                Number(value) / max * 90
                            )}%">
                        </div>

                        <span class="bar-label">
                            ${safe(name)}
                        </span>

                    </div>
                `
            ).join('');
    }

    function renderTable(resource) {

        page = resource;

        const config =
            configs[resource];

        if (!config) {
            return;
        }

        const liveLookup =
            $('#live-lookup');

        if (liveLookup) {
            liveLookup.hidden =
                resource !== 'air-quality';
        }

        $('#page-eyebrow').textContent =
            resource === 'air-quality'
                ? 'LIVE MONITORING'
                : 'OPERATIONS & OVERSIGHT';

        $('#page-title').textContent =
            labels[resource];

        $('#page-description').textContent =
            descriptions[resource];

        $('#table-title').textContent =
            config.title;

        $('#table-caption').textContent =
            'Live data from the monitoring platform';

        $('#add-button').innerHTML =
            `＋ <span>Add ${
                resource === 'limits'
                    ? 'limit'
                    : resource === 'air-quality'
                    ? 'reading'
                    : resource === 'emissions'
                    ? 'record'
                    : resource === 'violations'
                    ? 'violation'
                    : resource === 'payments'
                    ? 'payment'
                    : resource === 'fines'
                    ? 'fine'
                    : resource === 'stations'
                    ? 'station'
                    : 'industry'
            }</span>`;

        const rows =
            cache[resource] || [];

        $('#record-count').textContent =
            `${rows.length} record${
                rows.length === 1 ? '' : 's'
            }`;

        $('#data-head').innerHTML =
            `
                <tr>
                    ${
                        config.cols.map(
                            ([, label]) =>
                                `<th>${safe(
                                    label.toUpperCase()
                                )}</th>`
                        ).join('')
                    }

                    <th>ACTIONS</th>
                </tr>
            `;

        $('#data-body').innerHTML =
            rows.length

                ? rows.map(
                    row => `
                        <tr>
                            ${
                                config.cols.map(
                                    ([key]) =>
                                        `
                                        <td
                                            class="${
                                                key === 'industry'
                                                    ? 'industry-cell'
                                                    : ''
                                            }">
                                            ${cell(row, key)}
                                        </td>
                                        `
                                ).join('')
                            }

                            <td>
                                ${actionCell(
                                    row,
                                    resource
                                )}
                            </td>
                        </tr>
                    `
                ).join('')

                : `
                    <tr>
                        <td
                            colspan="${
                                config.cols.length + 1
                            }"
                            class="empty-cell">
                            No records yet.
                            Add a record to get started.
                        </td>
                    </tr>
                `;
    }

    function setPage(next) {

        if (next === 'dashboard') {

            page = next;

            $('.page-view.active')
                ?.classList.remove('active');

            $('#view-dashboard')
                .classList.add('active');

        } else {

            if (!configs[next]) {
                return;
            }

            $('.page-view.active')
                ?.classList.remove('active');

            $('#view-data')
                .classList.add('active');

            renderTable(next);
        }

        $$('.nav-item').forEach(
            nav =>
                nav.classList.toggle(
                    'active',
                    nav.dataset.page === next
                )
        );

        $('#breadcrumb').textContent =
            labels[next];

        $('#sidebar')
            .classList.remove('open');
    }

    function relationOptions(resource) {
        return cache[resource] || [];
    }

    function openForm(resource, item = null) {

        editing = item;

        const config =
            configs[resource];

        $('#dialog-title').textContent =
            `${item ? 'Edit' : 'Add'} ${
                config.title
                    .replace(/s$/, '')
                    .toLowerCase()
            }`;

        const form =
            $('#form-fields');

        form.innerHTML =
            config.fields.map(
                ([key, label, type, options]) => {

                    let value =
                        item?.[key];

                    if (
                        value &&
                        typeof value === 'object'
                    ) {
                        value = value.id;
                    }

                    if (type === 'relation') {

                        const list =
                            relationOptions(options);

                        return `
                            <div class="field">

                                <label
                                    for="field-${key}">
                                    ${safe(label)}
                                </label>

                                <select
                                    id="field-${key}"
                                    name="${key}"
                                    ${
                                        key === 'industry' ||
                                        key === 'emissionRecord'
                                            ? 'required'
                                            : ''
                                    }>

                                    <option value="">
                                        Select ${safe(
                                            label.toLowerCase()
                                        )}
                                    </option>

                                    ${
                                        list.map(
                                            option => `
                                                <option
                                                    value="${option.id}"
                                                    ${
                                                        String(
                                                            value ?? ''
                                                        ) ===
                                                        String(
                                                            option.id
                                                        )
                                                            ? 'selected'
                                                            : ''
                                                    }>
                                                    ${safe(
                                                        display(
                                                            option,
                                                            options ===
                                                                'industries'
                                                                ? 'industry'
                                                                : options ===
                                                                  'fines'
                                                                ? 'fine'
                                                                : options ===
                                                                  'violations'
                                                                ? 'violation'
                                                                : options ===
                                                                  'emissions'
                                                                ? 'emissionRecord'
                                                                : ''
                                                        )
                                                    )}
                                                </option>
                                            `
                                        ).join('')
                                    }

                                </select>

                            </div>
                        `;
                    }

                    if (type === 'select') {

                        return `
                            <div class="field">

                                <label
                                    for="field-${key}">
                                    ${safe(label)}
                                </label>

                                <select
                                    id="field-${key}"
                                    name="${key}">

                                    <option value="">
                                        Select…
                                    </option>

                                    ${
                                        options.map(
                                            option => `
                                                <option
                                                    ${
                                                        String(
                                                            value ?? ''
                                                        ).toUpperCase() ===
                                                        option
                                                            ? 'selected'
                                                            : ''
                                                    }>
                                                    ${safe(option)}
                                                </option>
                                            `
                                        ).join('')
                                    }

                                </select>

                            </div>
                        `;
                    }

                    const datetime =
                        type === 'datetime-local';

                    let inputValue =
                        value ?? '';

                    if (
                        datetime &&
                        inputValue
                    ) {
                        inputValue =
                            String(
                                inputValue
                            ).slice(0, 16);
                    }

                    const required =
                        [
                            'stationName',
                            'location',
                            'city',
                            'industryName',
                            'industryType',
                            'pollutant',
                            'permittedValue',
                            'fineAmount'
                        ].includes(key);

                    return `
                        <div class="field">

                            <label
                                for="field-${key}">
                                ${safe(label)}
                            </label>

                            <input
                                id="field-${key}"
                                name="${key}"
                                type="${type}"
                                value="${safe(
                                    inputValue
                                )}"
                                ${
                                    type === 'number'
                                        ? 'step="any"'
                                        : ''
                                }
                                ${
                                    required
                                        ? 'required'
                                        : ''
                                }>

                        </div>
                    `;
                }
            ).join('');

        $('#record-dialog')
            .showModal();
    }

    function formPayload(resource) {

        const payload = {};

        for (
            const [
                key,
                ,
                type
            ] of configs[resource].fields
        ) {

            const input =
                $(`#record-form [name="${key}"]`);

            if (
                !input ||
                input.value === ''
            ) {
                continue;
            }

            let value =
                input.value;

            if (type === 'number') {

                value =
                    Number(value);

            } else if (
                type === 'relation'
            ) {

                value = {
                    id: Number(value)
                };

            } else if (
                type === 'datetime-local'
            ) {

                value =
                    value.length === 16
                        ? `${value}:00`
                        : value;
            }

            payload[key] =
                value;
        }

        return payload;
    }

    async function saveForm(event) {

        event.preventDefault();

        if (
            !$('#record-form')
                .reportValidity()
        ) {
            return;
        }

        const resource =
            page;

        const body =
            formPayload(resource);

        const id =
            editing?.id;

        try {

            await api(
                `${endpoints[resource]}${
                    id ? `/${id}` : ''
                }`,
                {
                    method:
                        id ? 'PUT' : 'POST',

                    body:
                        JSON.stringify(body)
                }
            );

            $('#record-dialog')
                .close();

            notify(
                `${configs[resource].title.replace(
                    /s$/,
                    ''
                )} ${
                    id
                        ? 'updated'
                        : 'created'
                }`
            );

            await Promise.all(
                Object.keys(endpoints)
                    .map(load)
            );

            renderDashboard();

            renderTable(resource);

        } catch (error) {

            notify(
                `Could not delete record: ${error.message}`,
                true
            );
        }
    }

    async function remove(resource, id) {

        if (
            !confirm(
                'Delete this record? This action cannot be undone.'
            )
        ) {
            return;
        }

        try {

            await api(
                `${endpoints[resource]}/${id}`,
                {
                    method: 'DELETE'
                }
            );

            notify('Record deleted');

            await Promise.all(
                Object.keys(endpoints)
                    .map(load)
            );

            renderDashboard();

            renderTable(resource);

        } catch (error) {

            notify(
                `Could not delete record: ${error.message}`,
                true
            );
        }
    }

    async function liveLookup(event) {

        event.preventDefault();

        const city =
            $('#live-city')
                .value
                .trim();

        if (!city) {
            return;
        }

        try {

            const result =
                await api(
                    `${
                        endpoints['air-quality']
                    }/live?city=${
                        encodeURIComponent(city)
                    }`
                );

            cache['air-quality'] = [
                result,
                ...(cache['air-quality'] || [])
            ];

            renderDashboard();

            renderTable(
                'air-quality'
            );

            notify(
                `Live reading loaded for ${city}`
            );

        } catch (error) {

            notify(
                `Live lookup failed: ${error.message}`,
                true
            );
        }
    }

    async function detect(id) {

        try {

            const found =
                await api(
                    `${endpoints.violations}/detect/${id}`,
                    {
                        method: 'POST'
                    }
                );

            await Promise.all(
                Object.keys(endpoints)
                    .map(load)
            );

            renderDashboard();

            renderTable(
                'emissions'
            );

            notify(
                `${found.length} violation${
                    found.length === 1
                        ? ''
                        : 's'
                } detected`
            );

        } catch (error) {

            notify(
                `Detection failed: ${error.message}`,
                true
            );
        }
    }

    async function generate(id) {

        try {

            await api(
                `${endpoints.fines}/generate/${id}`,
                {
                    method: 'POST'
                }
            );

            await Promise.all(
                Object.keys(endpoints)
                    .map(load)
            );

            renderDashboard();

            renderTable(
                'violations'
            );

            notify(
                'Fine generated'
            );

        } catch (error) {

            notify(
                `Fine generation failed: ${error.message}`,
                true
            );
        }
    }

    async function pay(id) {

        const method =
            prompt(
                'Payment method (CARD, BANK_TRANSFER, CASH, UPI):',
                'CARD'
            );

        if (!method) {
            return;
        }

        try {

            await api(
                `${
                    endpoints.payments
                }/process/${id}?paymentMethod=${
                    encodeURIComponent(method)
                }`,
                {
                    method: 'POST'
                }
            );

            await Promise.all(
                Object.keys(endpoints)
                    .map(load)
            );

            renderDashboard();

            renderTable(
                'fines'
            );

            notify(
                'Payment processed successfully'
            );

        } catch (error) {

            notify(
                `Payment failed: ${error.message}`,
                true
            );
        }
    }

    document.addEventListener(
        'click',
        event => {

            const nav =
                event.target.closest(
                    '[data-page]'
                );

            if (nav) {

                event.preventDefault();

                setPage(
                    nav.dataset.page
                );

                return;
            }

            const refresh =
                event.target.closest(
                    '[data-action="refresh"]'
                );

            if (refresh) {

                refreshAll();

                return;
            }

            const edit =
                event.target.closest(
                    '[data-edit]'
                );

            if (edit) {

                openForm(
                    page,
                    (
                        cache[page] || []
                    ).find(
                        item =>
                            String(item.id) ===
                            edit.dataset.edit
                    )
                );

                return;
            }

            const del =
                event.target.closest(
                    '[data-delete]'
                );

            if (del) {

                remove(
                    page,
                    del.dataset.delete
                );

                return;
            }

            const detectButton =
                event.target.closest(
                    '[data-detect]'
                );

            if (detectButton) {

                detect(
                    detectButton.dataset.detect
                );

                return;
            }

            const generateButton =
                event.target.closest(
                    '[data-generate]'
                );

            if (generateButton) {

                generate(
                    generateButton.dataset.generate
                );

                return;
            }

            const payButton =
                event.target.closest(
                    '[data-pay]'
                );

            if (payButton) {

                pay(
                    payButton.dataset.pay
                );
            }
        }
    );

    $('#add-button')
        .addEventListener(
            'click',
            () => openForm(page)
        );

    $('#record-form')
        .addEventListener(
            'submit',
            saveForm
        );

    $('#live-form')
        .addEventListener(
            'submit',
            liveLookup
        );

    $('#menu-toggle')
        .addEventListener(
            'click',
            () =>
                $('#sidebar')
                    .classList
                    .toggle('open')
        );

    $('#today').textContent =
        new Intl.DateTimeFormat(
            undefined,
            {
                weekday: 'short',
                month: 'short',
                day: 'numeric'
            }
        ).format(new Date());

    refreshAll();

})();
