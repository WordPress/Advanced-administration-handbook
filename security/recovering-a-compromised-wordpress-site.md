# Recovering a compromised WordPress site

If your WordPress site may be compromised, contact your hosting provider from a device and account you trust. Ask the provider to preserve logs and snapshots and help restrict access if the site is serving phishing pages, malware, or harmful redirects. If you manage the site for someone else, notify the site owner and agree on who can authorize changes and communicate with affected users.

This guide is for WordPress administrators and hosting operators. It applies NIST's Detect, Respond, and Recover functions to WordPress, with operational steps adapted from CISA's incident-response playbook. Investigation and containment may overlap as evidence changes. [NIST SP 800-61 Rev. 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final) · [CISA incident-response playbook](https://www.cisa.gov/sites/default/files/2024-08/Federal_Government_Cybersecurity_Incident_and_Vulnerability_Response_Playbooks_508C.pdf)

## Detect and assess the incident

Start with the report or alert that brought the problem to your attention. Record when it appeared, which URLs or accounts were involved, and what you can verify. Examples include an unfamiliar administrator, unexpected plugin or file changes, redirects reported by visitors, and warnings from your host or a search engine. Check independent sources, such as hosting audit logs, web server logs, and a known release or deployment. A single alert can have several explanations.

Identify which sites, accounts, and services share the affected hosting account, credentials, or database. In a multisite network, include network administrators and every site that uses the affected code or credentials. Keep a timeline with time zones, observed facts, working hypotheses, and actions taken. If the scope may extend to hosting, DNS, or personal or payment data, involve the host and the people responsible for incident response and notifications early. [NIST SP 800-61 Rev. 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final) · [WordPress monitoring](https://developer.wordpress.org/advanced-administration/security/monitoring/)

## Contain the incident and preserve evidence

Use controls outside the suspected WordPress installation where possible. Ask the host to isolate the site, restrict access at the edge, or serve a temporary maintenance page from a trusted system. Coordinate the order of changes with the host. Act promptly to protect visitors when harm is ongoing and record what changed.

Before overwriting files, restoring a backup, or removing accounts, capture the evidence you can safely retain:

- A host snapshot or copies of the files and database, including configuration and content outside the usual WordPress directories.
- Available access, error, authentication, deployment, and hosting audit logs. Ask the provider to preserve logs before they rotate.
- Alert details, example URLs, affected accounts, file paths, and timestamps.

Store the copies away from the affected server with restricted access. Record the source, collection time, and collector, and keep originals separate from working copies. Treat snapshots and dumps as sensitive and potentially unsafe to run. [CISA incident-response playbook](https://www.cisa.gov/sites/default/files/2024-08/Federal_Government_Cybersecurity_Incident_and_Vulnerability_Response_Playbooks_508C.pdf) · [WordPress backups](https://developer.wordpress.org/advanced-administration/security/backup/)

## Investigate how access was gained and maintained

Work from the preserved copy and correlate evidence across systems. Use the first verified malicious change to guide the search further back. For example, compare when an unfamiliar administrator appeared with hosting sign-ins, WordPress authentication records, and changes to plugins or deployment credentials. Identify likely entry points, affected components, any continuing access, and the evidence behind each conclusion. Record gaps explicitly. If the entry point remains uncertain, document which plausible paths you closed before returning the site to service.

Review administrator and multisite super administrator accounts, active and inactive plugins and themes, must-use plugins, drop-ins, uploads, scheduled jobs, and unexpected changes to `wp-config.php`, `.htaccess`, or web server configuration. Review database content and settings for unauthorized users, injected content, altered site URLs, and unexpected plugin or option changes. Ask your host to check areas you cannot inspect, including sibling sites, hosting jobs, and account-level access.

Compare files against known releases or your deployment repository. On an isolated working copy, `wp core verify-checksums --include-root` compares WordPress core files to the matching WordPress.org release and also reports unexpected items in the root. `wp plugin verify-checksums --all` compares eligible plugins with WordPress.org checksums. The plugin command loads WordPress, so run it only once the copy is safe to execute. Compare custom and commercial code to a trusted repository or vendor package. Checksums cover the files for which a reference exists. Review the database, uploads, and configuration separately. [NIST incident analysis guidance](https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-61r3.pdf) · [Core checksum command](https://developer.wordpress.org/cli/commands/core/verify-checksums/) · [Plugin checksum command](https://developer.wordpress.org/cli/commands/plugin/verify-checksums/)

## Remove unauthorized access and rebuild or restore

Choose a recovery path based on the scope you can establish and the backups you can verify.

- **Restore a verified backup in an isolated environment.** Choose a backup from before the earliest plausible compromise. Check both the files and database before use. Account for changes made since the backup, such as orders, posts, and uploads. Review any data you bring forward before importing it.
- **Rebuild from trusted sources if you lack a verified backup or cannot establish the scope.** Install supported WordPress, plugins, and themes from trusted sources in a clean environment. Recreate configuration, then import reviewed content and media. If evidence supports a cleanup in place, coordinate it with your host and independently verify the files, data, configuration, and accounts before reopening.

Close the known or plausible entry paths. Update or remove the affected component, correct access and configuration problems, and remove unauthorized users, files, scheduled tasks, and settings. Replace compromised software directories with clean copies so extra files cannot survive an overwrite. If the hosting account or server may have been compromised, have the provider secure or replace that environment before putting the site back online. [CISA eradication and recovery guidance](https://www.cisa.gov/sites/default/files/2024-08/Federal_Government_Cybersecurity_Incident_and_Vulnerability_Response_Playbooks_508C.pdf) · [NIST recovery guidance](https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-61r3.pdf)

From a trusted device, secure the accounts that control recovery, including hosting, DNS, and deployment. Revoke unauthorized access and invalidate active sessions. Rotate affected WordPress passwords, [application passwords](https://developer.wordpress.org/advanced-administration/security/application-passwords/), database and SFTP credentials, and integration keys as appropriate. Replace WordPress authentication keys and salts in the recovered configuration to invalidate existing login cookies. Coordinate credential changes with the services that use them, and keep replacement secrets out of the affected environment. [WordPress hardening guidance](https://developer.wordpress.org/advanced-administration/security/hardening/)

## Verify recovery before reopening

Confirm that the known signs of compromise are absent, the suspected entry paths are closed, and the restored site works. Check representative pages from a logged-out session, administrative access, redirects, forms, and any site-specific critical workflow, such as checkout. Review logs during testing and take a fresh backup after validation. Reopen in coordination with the host or site owner and monitor for recurrence.

If a browser, search engine, or blocklist still warns visitors, use that service's official review process after you have corrected the issues across the affected site. For example, request a review through Google's Security Issues report if it lists your site. Keep records of the changes and the review status. [NIST recovery verification guidance](https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-61r3.pdf) · [Google Security Issues report](https://support.google.com/webmasters/answer/9044101)

## Harden the recovered site and record lessons learned

Write a short incident record covering the timeline, scope, likely root cause, evidence gaps, and recovery decisions. Assign an owner and due date to each follow-up. Prioritize controls that address the incident, such as timely updates, removing unused components, least-privilege accounts, multifactor authentication for administrators, and alerts for unexpected account or file changes. Keep backups outside the hosting account where practical, test restoration, and review whether log retention was long enough for the investigation. Update your response contacts and procedures while the incident is fresh. [WordPress hardening guidance](https://developer.wordpress.org/advanced-administration/security/hardening/) · [WordPress multifactor authentication](https://developer.wordpress.org/advanced-administration/security/mfa/) · [NIST SP 800-61 Rev. 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final)
