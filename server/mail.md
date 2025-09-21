# Mail

This page details the philosphy, operations, and the recommended practices for setting up the local mailing environment for WordPress.

## Prerequisites

Before starting, you'll need to have a basic understanding of the [SMTP protocol](https://en.wikipedia.org/wiki/Simple_Mail_Transfer_Protocol) and the roles of each of the three mailing agents ([MUA](https://en.wikipedia.org/wiki/Mail_user_agent), [MTA](https://en.wikipedia.org/wiki/Message_transfer_agent), and [MDA](https://en.wikipedia.org/wiki/Message_delivery_agent)).

## WordPress's Philosophy on Mailing

WordPress has never provided either a MUA or an MTA by default. It only provides a wrapper for a language API library that handles the submission of a message to an MTA which can interact either with PHP `mail()` function or the `SMTP` extension.

This means, that its the server administrator's responsibility to configure the MTA and the related mailing environment properly to interact with the chosen language API. Historically, [PHPMailer](https://github.com/PHPMailer/PHPMailer) has been this library of choice. WordPress is not involved in the process of sending emails but only in the process of formatting the message to submit it to this mailing API.

## How WordPress's Mailing operates out-of-the-box

Currently, WordPress the `wp_mail()` wrapping function. By default configuration, this wrapper informs the PHPMailer library to use the PHP's internal `mail()` function that requires the presence of a sendmail-compatible binary acting as the MTA. Hence, in order for WordPress to work out-of-the-box, one needs to have the related mailing local environment properly configured.

This is generally not a problem if you are using a managed hosting service, but if you are running your own server and do not have a SMTP server (MTA) set up and/or the related mailing environment properly configured, the outgoing mail won't be sent, unless you configure WordPress' wrapper to use a remote SMTP server.

## Recommended Actions

Besides the technical aspects, it's worthwhile to outline the recommended solutions given the different scenarios so the admins do not end up choosing solutions that don't fit them.

If your WordPress site is on managed hosting, then there's typically nothing extra you need to do to send emails, but you may need to take some additional actions to ensure deliverability as explained below. Mails originated from your WordPress install shall work out-of-the-box. All mailing related questions shall be directed to your webhost, unless the host provides the option to configure a SMTP relay through their admin panel.

If you're on a self-managed hosting (i.e. running your own server), there are typically three ways you can setup your mails:

- **Setup a local MTA or a dedicated mail server that can send mails directly**. In this method, you as the admin is responsible for everything. Please keep in mind, even if you've configured all the related software correctly, you may still run into issues as mail deliverability extend into factors outside of your control such as IP reputation. Due to the amount of overhead and ongoing maintenance required, this method is typically reserved for server administrators with experience in this field, because you might eventually run into serious deliverability troubles.

- **Setup a local MTA and have it relay mails to a third party mailing service**. This is the recommended method for most self-hosted setups, is easier to implement than the previous one, and still retains compatibility with WordPress' philosophy. If you don't want to deal with the complexity of mail deliverability issues in running your full MTA, this is way to go. In fact, some smaller web hosts utilize this method (they would hold a business account with the third party mailing services and relay all the mails to the mailing service). If you host and manage your own site and you are sending through WordPress a sizable number of emails, this method is also ideal as you delegate the responsibility to the third party mailing service. Like the first method, Be aware that this option is not exempt of risks as we will explain in the next section. Setting up your MTA always requires some level of server management expertise, even if you are relying on a third party mailing service.

- **Configure a SMTP relay via code to utilize a third party mailing service**. This is the recommended method if you are not going to handle too many emails or you don't want to deal with the complexity of mail deliverability issues in running a self-hosted MTA. Be aware that this method [will cause a performance degradation](https://konstantin.blog/2021/wp_mail-is-not-broken/) for many reasons.

## Setting up your own MTA

If you choose to set up your MTA, either for local or third party relay, here we are going to briefly explain the basics of setting up your MTA in both *NIX and Windows environments. Before we go into the details, be aware that there are some risks in this approach. Port 25 has to be open, which can be problematic if you are not careful as it is one of the most common attack vectors and if you don't protect the SMTP relay authentication properly, you might end up with a security issue or having your server being used for spamming.

### *NIX Server Basics

If you are running your own *NIX server, you should have either `postfix` `exim`, `qmail` or `sendmail` as the most popular MTA, on your machine and just need to set them up (you may search on the web for how-tos). If you wish to use a third party mailing service, the recommended practice is to setup your local MTA to relay mail to the remote SMTP server as this will prevent the page load time from suffering. 

### Windows Host Server Specific

Check your “Relay” settings on the SMTP Virtual Server. Grant access to `127.0.0.1` . Then in your `php.ini` file, set the `SMTP` setting to the same IP address. Also set `smtp_port` to `25`.

On a Windows machine, try a sendmail emulator like [Fake sendmail for Windows with TLS v1.2 support](https://github.com/sendmail-tls1-2/main).

### Alternative Solutions to MTA Setup

If you do not want to manually setup a complete MTA in your box, there are some other options:

* Use a relay MTA in place of a local full-featured MTA: for example, [msmtp-mta](https://wiki.debian.org/msmtp) is a simplified MTA that can be used to relay emails using a remote SMTP server (i.e. a remote MTA). The package provides a `/usr/sbin/sendmail` symlink to `msmtp` and has sendmail compatible interface that other software can use directly.  

* Use SMTP plugins: You can install [SMTP plugins](https://wordpress.org/plugins/search/smtp/) from the WP.org plugin directory. These type of plugins overrides `wp_mail()` default settings to use PHPMailer or any other mailing library of their choice, which utlize remote third party mailing services to send mail on your behalf of your domain.

### Ensuring Deliverability

Once we have our system sending mails, we need to ensure that they are actually delivered to the recipients. There are a few things we need to check:

#### Ensure Proper Return Address is Used

By default, the WordPress wrapper fills in the `From:` field with `wordpress@example.com` and the `From:` name as *WordPress*. This is fine if this is a valid e-mail address. For example, if your domain is `example.com`, your host shall pass `wordpress@example.com` on for delivery. It will probably send your mail as long as `example.com` is setup to send and receive mail, even if `wordpress` is not a valid mailbox. Some old mail delivery agents will check if the `From:` address is valid and can receive mails. This check is becoming less common nowadays, but we recommend to make sure to use a valid email address and the mailbox exists in your receiving mail server. If you are using the default `From:` address, simply verify that you can receive mails in the `wordpress@example.com` mailbox. Remember that `example.com` is the domain of your WordPress installation.

But if you don't want to send the emails from the same domain as the one exhibited in your installation then you must introduce some code changes in your WordPress. For example, if your WP domain is `example.org` but you want to send the emails from `mail.example.org` the mail may not send because the DNS records of `example.org` might not authorize its mails to be handled by your mail server, because the default `From:` address will be `wordpress@wordpress.org`. To change the default `From:` address, your wordpress can [use the `wp_mail_from` filter](https://developer.wordpress.org/reference/hooks/wp_mail_from/). For the name, you can use [the `wp_mail_from_name` filter](https://developer.wordpress.org/reference/hooks/wp_mail_from_name/). 

Also be aware that you can also force the `From:` address configuration in your configured MTA server. Refer to the related documentation for your MTA.

#### The Reply-To Address

The WordPress wrapper ignores the `Reply-To:`, so it will be coincidental with the `From:` address, following the example above, `wordpress@example.com` by default. If you want your users to be able to reply to your mails make sure that this mailbox exists in your receiving mail server. The default `Reply-To:` is always going to be the same as the `From:` address unless you explicitly set it to a different address in the mail headers. 

There is only one scenario where the `Reply-To:` address is set automatically: when we are sending an email to a user that commented on a post. In this case, the `Reply-To:` address will be set to the email address that the commenter explicitely specified.

### Troubleshooting Spam Deliverability Issues

Your email message may have been routed to a spam folder or even worse, simply discarded as malicious. There are a couple measures you can use to convince recipient’s mail servers that your message is legitimate and should be delivered as addressed. Setting up SPF, DKIM and DMARC is critical nowadays to ensure deliverability. There are multiple guides online that will help you set up these records. Here we are going to explain why you should take care of them.

#### SPF (Sender Policy Framework)

This is the most common anti-spam measure used. The MDA checks the SPF record of the sender's domain to see if it is authorized to send mail for that domain. The SPF is a TXT DNS record that you need to add or check if your hosting has added it for you, that lists the IP addresses or hostnames that are authorized to send mail for that domain. This ensures that the mail is not coming from an unauthorized source. So for example, if an unknown server IP address sends an email with your domain name in the `From:` field, thanks to the SPF DNS record, the MDA will reject the mail as spam. But if you don't configure this properly in your DNS records, your mails will be also rejected as spam.

Just remember, if you are sending emails through different servers, including your WordPress installation, you need to add all the addresses to the SPF record.

#### DKIM (Domain Key Identified Mail)

This antispam integrity authentication check has gain a lot of traction in the last few years and has been implemented by most major email providers. You can use both SPF and DKIM in the same message. Again, just as with SPF, you can check if your receiving mailserver verified your host’s domain key by examining the mail header. Also as with SPF, if you can edit your TXT DNS records and the mail server belongs to your domain, you can set up DKIM credentials yourself. If you are in a managed environment, check with your hosting provider if they have set this up for you.

In case you have to manage this by yourself, to send the right DKIM signature in your email there are multiple methods:

* Configure your MTA to sign the email with the DKIM signature. 

* Some SMTP plugins have a DKIM signature option.

* Use `PHPMailer` library to set the DKIM signature. For this, you may need to [hook to the `phpmailer_init` action](https://developer.wordpress.org/reference/hooks/phpmailer_init/) and follow the PHPMailer library's documentation (this is not officially documented, check [this post for more details](https://stackoverflow.com/a/24464694/4442122).

Also make sure to generate your DKIM keys with the correct domain information and publish the DNS record accordingly. When you add the DKIM public key to your DNS records, the MDA will check for your email header DKIM-Signature and verify the signature. If the signature is invalid, the MDA will reject the mail as spam. This is meant to avoid emails being spoofed and sent from an unauthorized source. DKIM and SPF are used together to ensure the email is legitimate and should be delivered as addressed.

#### DMARC (Domain-based Message Authentication, Reporting & Conformance)

The DMARC record is another TXT DNS record that you need to add or check if your hosting has added it for you, that lists the actions to take when the email has not passed the SPF or DKIM checks. DMARC is becoming almost mandatory, so to ensure deliverability, we recommend setting it up. You can read more about it [here](https://dmarc.org/).