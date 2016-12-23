export function getCookie(name) {
  const regexp = new RegExp(`(?:^${name}|;\s*${name})=(.*?)(?:;|$)`, "g");
  const result = regexp.exec(document.cookie);
  return (result === null) ? null : result[1];
}
